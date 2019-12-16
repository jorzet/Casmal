package com.jorzet.casmal.fragments.question

import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseQuestionFragment
import com.jorzet.casmal.models.Question

class MultipleQuestionFragment(override var mQuestion: Question) : BaseQuestionFragment() {

    private lateinit var mText: TextView
    private lateinit var mOptionA: TextView
    private lateinit var mOptionB: TextView
    private lateinit var mOptionC: TextView
    private lateinit var mOptionD: TextView
    private lateinit var mOptionAView: View
    private lateinit var mOptionBView: View
    private lateinit var mOptionCView: View
    private lateinit var mOptionDView: View

    override fun getLayoutId(): Int {
        return R.layout.multiple_question_fragment
    }

    override fun getFragmentActivity(): FragmentActivity {
        return this.activity!!
    }

    override fun initView() {
        mText = rootView.findViewById(R.id.tv_text)
        mOptionA = rootView.findViewById(R.id.tv_option_a)
        mOptionB = rootView.findViewById(R.id.tv_option_b)
        mOptionC = rootView.findViewById(R.id.tv_option_c)
        mOptionD = rootView.findViewById(R.id.tv_option_d)

        mOptionAView = rootView.findViewById(R.id.rl_option_a)
        mOptionBView = rootView.findViewById(R.id.rl_option_b)
        mOptionCView = rootView.findViewById(R.id.rl_option_c)
        mOptionDView = rootView.findViewById(R.id.rl_option_d_)
    }

    override fun prepareComponents() {
        onUpdateQuestionView()
    }

    override fun onUpdateQuestionView() {

        mText.text = mQuestion.text

        if (mQuestion.opt1.isNotEmpty())
            mOptionA.text = mQuestion.opt1
        else
            mOptionAView.visibility = View.GONE

        if (mQuestion.opt2.isNotEmpty())
            mOptionB.text = mQuestion.opt2
        else
            mOptionBView.visibility = View.GONE

        if (mQuestion.opt3.isNotEmpty())
            mOptionC.text = mQuestion.opt3
        else
            mOptionCView.visibility = View.GONE

        if (mQuestion.opt4.isNotEmpty())
            mOptionD.text = mQuestion.opt4
        else
            mOptionDView.visibility = View.GONE
    }

    override fun showAnswer() {

    }

}