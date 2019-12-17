package com.jorzet.casmal.fragments.question

import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseQuestionFragment
import com.jorzet.casmal.models.Question

class MultipleQuestionFragment(override var mQuestion: Question) : BaseQuestionFragment(){

    private lateinit var mText: TextView
    private lateinit var mOptionA: TextView
    private lateinit var mOptionB: TextView
    private lateinit var mOptionC: TextView
    private lateinit var mOptionD: TextView
    private lateinit var mOptionABackgroundView: View
    private lateinit var mOptionBBackgroundView: View
    private lateinit var mOptionCBackgroundView: View
    private lateinit var mOptionDBackgroundView: View
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
        mOptionABackgroundView = rootView.findViewById(R.id.option_a)
        mOptionBBackgroundView = rootView.findViewById(R.id.option_b)
        mOptionCBackgroundView = rootView.findViewById(R.id.option_c)
        mOptionDBackgroundView = rootView.findViewById(R.id.option_d)
        mOptionAView = rootView.findViewById(R.id.rl_option_a)
        mOptionBView = rootView.findViewById(R.id.rl_option_b)
        mOptionCView = rootView.findViewById(R.id.rl_option_c)
        mOptionDView = rootView.findViewById(R.id.rl_option_d_)
    }

    override fun prepareComponents() {
        mOptionABackgroundView.setOnClickListener {

            mOptionABackgroundView.background =
                if (mQuestion.answer == "1")
                    resources.getDrawable(R.drawable.answer_correct_option_background)
                else
                    resources.getDrawable(R.drawable.answer_wrong_option_background)

            mOptionBBackgroundView.setOnClickListener(null)
            mOptionCBackgroundView.setOnClickListener(null)
            mOptionDBackgroundView.setOnClickListener(null)

            mQuestion.wasOK = mQuestion.answer == "1"
            mQuestion.answered = true
            mQuestion.chosenOption = "1"
        }
        mOptionBBackgroundView.setOnClickListener {

            mOptionBBackgroundView.background =
                if (mQuestion.answer == "2")
                    resources.getDrawable(R.drawable.answer_correct_option_background)
                else
                    resources.getDrawable(R.drawable.answer_wrong_option_background)

            mOptionABackgroundView.setOnClickListener(null)
            mOptionCBackgroundView.setOnClickListener(null)
            mOptionDBackgroundView.setOnClickListener(null)

            mQuestion.wasOK = mQuestion.answer == "2"
            mQuestion.answered = true
            mQuestion.chosenOption = "2"
        }
        mOptionCBackgroundView.setOnClickListener {

            mOptionCBackgroundView.background =
                if (mQuestion.answer == "3")
                    resources.getDrawable(R.drawable.answer_correct_option_background)
                else
                    resources.getDrawable(R.drawable.answer_wrong_option_background)

            mOptionABackgroundView.setOnClickListener(null)
            mOptionBBackgroundView.setOnClickListener(null)
            mOptionDBackgroundView.setOnClickListener(null)

            mQuestion.wasOK = mQuestion.answer == "3"
            mQuestion.answered = true
            mQuestion.chosenOption = "3"
        }
        mOptionDBackgroundView.setOnClickListener {

            mOptionDBackgroundView.background =
                if (mQuestion.answer == "4")
                    resources.getDrawable(R.drawable.answer_correct_option_background)
                else
                    resources.getDrawable(R.drawable.answer_wrong_option_background)

            mOptionABackgroundView.setOnClickListener(null)
            mOptionBBackgroundView.setOnClickListener(null)
            mOptionCBackgroundView.setOnClickListener(null)

            mQuestion.wasOK = mQuestion.answer == "4"
            mQuestion.answered = true
            mQuestion.chosenOption = "4"
        }
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
        if (mQuestion.answer.isNotEmpty()) {
            when(mQuestion.answer) {
                "1" -> {
                    mOptionABackgroundView.background =
                        resources.getDrawable(R.drawable.answer_response_background)
                }
                "2" -> {
                    mOptionBBackgroundView.background =
                        resources.getDrawable(R.drawable.answer_response_background)
                }
                "3" -> {
                    mOptionCBackgroundView.background =
                        resources.getDrawable(R.drawable.answer_response_background)
                }
                "4" -> {
                    mOptionDBackgroundView.background =
                        resources.getDrawable(R.drawable.answer_response_background)
                }
            }
            mQuestion.wasOK = false
            mQuestion.answered = false
            mQuestion.chosenOption = "-1"
        }
    }

}