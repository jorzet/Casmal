/*
 * Copyright [2020] [Jorge Zepeda Tinoco]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jorzet.casmal.dialogs

import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseDialog
import com.jorzet.casmal.managers.FirebaseStorageManager
import com.jorzet.casmal.managers.ImageManager

class FullScreeImageDialog: BaseDialog() {

    /**
     * UI accessors
     */
    private lateinit var mFlashCard: ImageView

    companion object {
        private const val ARG_FLASH_CARD: String = "arg_flash_card"

        fun newInstance(flashCard: String) : FullScreeImageDialog {
            val fullScreeImageDialog = FullScreeImageDialog()

            val args = Bundle()
            args.putString(ARG_FLASH_CARD, flashCard)

            fullScreeImageDialog.arguments = args

            return fullScreeImageDialog
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_full_screen_image
    }

    override fun getDialogStyle(): Int {
        return R.style.FullScreenDialog
    }

    override fun initView() {
        mFlashCard = rootView.findViewById(R.id.iv_full_screen_image)
    }

    override fun prepareComponents() {
        val flashcard = arguments!!.getString(ARG_FLASH_CARD, null)
        if (flashcard != null && flashcard.isNotEmpty()) {
            val storageReference = FirebaseStorageManager.getImage(flashcard)
            mFlashCard.layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            ImageManager.instance.setImage(storageReference, mFlashCard)
        }
    }
}