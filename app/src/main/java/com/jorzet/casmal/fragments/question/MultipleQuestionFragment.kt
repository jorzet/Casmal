package com.jorzet.casmal.fragments.question

import android.widget.TextView
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseQuestionFrgament
import com.jorzet.casmal.models.Question

class MultipleQuestionFragment(override var mQuestion: Question) : BaseQuestionFrgament() {

    private lateinit var mText: TextView
    private lateinit var mOptionA: TextView
    private lateinit var mOptionB: TextView
    private lateinit var mOptionC: TextView
    private lateinit var mOptionD: TextView



    override fun getLayoutId(): Int {
        return R.layout.multiple_question_fragment
    }

    override fun initView() {
        mText = rootView.findViewById(R.id.tv_text)
        mOptionA = rootView.findViewById(R.id.tv_option_a)
        mOptionB = rootView.findViewById(R.id.tv_option_b)
        mOptionC = rootView.findViewById(R.id.tv_option_c)
        mOptionD = rootView.findViewById(R.id.tv_option_d)
    }

    override fun prepareComponents() {
        onUpdateQuestionView()
    }

    override fun onUpdateQuestionView() {
        mText.text = mQuestion.text
        mOptionA.text = mQuestion.opt1
        mOptionB.text = mQuestion.opt2
        mOptionC.text = mQuestion.opt3
        mOptionD.text = mQuestion.opt4
    }

}