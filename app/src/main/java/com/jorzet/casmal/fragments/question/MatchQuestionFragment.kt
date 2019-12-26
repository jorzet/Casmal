package com.jorzet.casmal.fragments.question

import com.jorzet.casmal.base.BaseQuestionFragment
import com.jorzet.casmal.models.Question
import com.jorzet.casmal.ui.QuestionActivity

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */


class MatchQuestionFragment(
    override var mQuestion: Question,
    override var mActivity: QuestionActivity
) : BaseQuestionFragment() {


    override fun onUpdateQuestionView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAnswer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun prepareComponents() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}