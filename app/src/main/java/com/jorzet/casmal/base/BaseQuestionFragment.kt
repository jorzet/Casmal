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

package com.jorzet.casmal.base

import android.util.Log
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.Average
import com.jorzet.casmal.models.Level
import com.jorzet.casmal.models.Question
import com.jorzet.casmal.ui.QuestionActivity
import com.jorzet.casmal.models.User
import com.jorzet.casmal.models.FlashCard

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

abstract class BaseQuestionFragment: BaseFragment() {

    /**
     * Constants
     */
    companion object {
        const val TAG: String = "BaseQuestionFragment"
    }

    /**
     * Attributes
     */
    abstract var mQuestion: Question
    abstract var mActivity: QuestionActivity

    /** Option Selected Listener */
    interface OnOptionSelectedListener {
        fun onButtonsEnable()
        fun onNextQuestionButtonEnable(enable: Boolean)
        fun onOptionSelected(question: Question)
    }

    /** Level Up Listener */
    interface OnLevelUpListener {
        /**
         * This method handle [User] level and points to show new [FlashCard]
         */
        fun onLevelUp(level: Level)

        /**
         * This method return current flash card name according user level
         * @return flash card name
         */
        fun getFlashCard(level: Level): String?
    }

    /**
     * Update current fragment according new [Question]
     */
    abstract fun onUpdateQuestionView()

    /**
     * Handle components to show answer
     */
    abstract fun showAnswer()

    open fun onPushAverage(average: Average, isExam: Boolean) {
        val mFirebaseRequestManager: FirebaseRequestManager =
            FirebaseRequestManager.getInstance(mActivity)

        mFirebaseRequestManager.pushAverage(isExam, average, object: FirebaseRequestManager.OnPushAverageListener {
            override fun onPushAverageSuccess() {
                Log.d(TAG, "push question success")
            }

            override fun onPushAverageFail(throwable: Throwable) {
                Log.d(TAG, "push question fail")
            }
        })
    }
}