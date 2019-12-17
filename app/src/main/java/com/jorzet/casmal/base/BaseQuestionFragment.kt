package com.jorzet.casmal.base

import com.jorzet.casmal.models.Question
import com.jorzet.casmal.ui.QuestionActivity

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

abstract class BaseQuestionFragment: BaseFragment() {

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
        fun onOptionCorrect()
        fun onOptionIncorrect()
    }

    /**
     *
     */
    abstract fun onUpdateQuestionView()

    /**
     *
     */
    abstract fun showAnswer()
}