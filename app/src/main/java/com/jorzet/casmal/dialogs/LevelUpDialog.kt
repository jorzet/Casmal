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

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseDialog
import com.jorzet.casmal.managers.FirebaseStorageManager
import com.jorzet.casmal.managers.ImageManager
import java.lang.Exception

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 10/01/20.
 */

class LevelUpDialog: BaseDialog() {

    /**
     * UI accessors
     */
    private lateinit var mOkButton: View
    private lateinit var mLevel: TextView
    private lateinit var mFlashCard: ImageView

    private var onOkButtonListener: OnOkButtonListener? = null

    interface OnOkButtonListener {
        fun onOkButtonClick()
    }

    companion object {
        private const val ARG_LEVEL: String = "arg_level"
        private const val ARG_FLASH_CARD: String = "arg_flash_card"
        private const val ARG_IS_LISTENER_ACTIVITY : String = "arg_is_listener_activity"

        fun newInstance(level: Int, flashCard: String, onOkButtonListener: OnOkButtonListener) : LevelUpDialog {
            val levelUpDialog = LevelUpDialog()

            val args = Bundle()

            args.putInt(ARG_LEVEL, level)
            args.putString(ARG_FLASH_CARD, flashCard)

            if (onOkButtonListener is androidx.appcompat.app.AppCompatActivity) {
                args.putBoolean(ARG_IS_LISTENER_ACTIVITY, true)
            } else {
                args.putBoolean(ARG_IS_LISTENER_ACTIVITY, false)
                levelUpDialog.setTargetFragment(onOkButtonListener as androidx.fragment.app.Fragment, 0)
            }

            levelUpDialog.arguments = args

            return levelUpDialog
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_level_up
    }

    override fun initView() {
        mOkButton = rootView.findViewById(R.id.btn_ok)
        mLevel = rootView.findViewById(R.id.tv_level)
        mFlashCard = rootView.findViewById(R.id.ivFlashCard)
        mFlashCard.setOnClickListener {
            val flashcard = arguments!!.getString(ARG_FLASH_CARD, null)

            // show full screen image
            FullScreeImageDialog
                .newInstance(flashcard)
                .show(fragmentManager!!,"full_screen_image")
        }
    }

    override fun prepareComponents() {

        mLevel.text = arguments!!.getInt(ARG_LEVEL).toString()

        val flashcard = arguments!!.getString(ARG_FLASH_CARD, null)
        if (flashcard != null && flashcard.isNotEmpty()) {
            val storageReference = FirebaseStorageManager.getImage(flashcard)
            ImageManager.getInstance().setImage(storageReference, mFlashCard)
        }
        mOkButton.setOnClickListener {
            dismiss()
            onOkButtonListener?.onOkButtonClick()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onOkButtonListener =
                if (arguments!!.getBoolean(ARG_IS_LISTENER_ACTIVITY)) {
                    activity as OnOkButtonListener
                } else {
                    targetFragment as OnOkButtonListener
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDetach() {
        super.onDetach()
        onOkButtonListener = null
    }


}