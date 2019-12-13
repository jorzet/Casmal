package com.jorzet.casmal.base

import com.jorzet.casmal.interfaces.QuestionView
import com.jorzet.casmal.models.Question

abstract class BaseQuestionFrgament: BaseFragment(), QuestionView {
    abstract var mQuestion: Question

}