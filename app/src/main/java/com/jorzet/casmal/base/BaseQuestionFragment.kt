package com.jorzet.casmal.base

import android.util.Log
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.Average
import com.jorzet.casmal.models.Question
import com.jorzet.casmal.ui.QuestionActivity

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

    /**
     * Listener
     */
    interface OnOptionSelectedListener {
        fun onButtonsEnable()
        fun onNextQuestionButtonEnable(enable: Boolean)
        fun onOptionSelected(question: Question)
    }

    interface OnLevelUpListener {
        fun onLevelUp()
    }

    /**
     *
     */
    abstract fun onUpdateQuestionView()

    /**
     *
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