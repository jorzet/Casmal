package com.jorzet.casmal.fragments.question

import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseQuestionFrgament
import com.jorzet.casmal.interfaces.QuestionView
import com.jorzet.casmal.models.Question

class TrueFalseQuestionFragment(override var mQuestion: Question) : BaseQuestionFrgament(){

    private lateinit var mText: TextView
    private lateinit var mOptionTrue: TextView
    private lateinit var mOptionFalse: TextView

    override fun getLayoutId(): Int {
        return R.layout.true_false_question_fragment
    }

    override fun getFragmentActivity(): FragmentActivity {
        return this.activity!!
    }

    override fun initView() {
        mText = rootView.findViewById(R.id.tv_text)
        mOptionTrue = rootView.findViewById(R.id.tv_option_true)
        mOptionFalse = rootView.findViewById(R.id.tv_option_false)
    }

    override fun prepareComponents() {

    }

    override fun onUpdateQuestionView() {
        mText.text = mQuestion.text
        mOptionTrue.text = mQuestion.opt1
        mOptionFalse.text = mQuestion.opt2
    }

}