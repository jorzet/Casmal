package com.jorzet.casmal.base

import com.jorzet.casmal.models.Question

abstract class BaseQuestionFragment: BaseFragment() {
    abstract var mQuestion: Question

    abstract fun onUpdateQuestionView()
    abstract fun showAnswer()
}