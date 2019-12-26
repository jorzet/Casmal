package com.jorzet.casmal.fragments.question

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseQuestionFragment
import com.jorzet.casmal.models.Question
import com.jorzet.casmal.ui.QuestionActivity

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

class TrueFalseQuestionFragment(override var mQuestion: Question,
                                override var mActivity: QuestionActivity
) : BaseQuestionFragment(){

    /**
     * UI accessors
     */
    private lateinit var mText: TextView
    private lateinit var mOptionTrue: TextView
    private lateinit var mOptionFalse: TextView
    private lateinit var mOptionTrueBackgroundView: View
    private lateinit var mOptionFalseBackgroundView: View
    private lateinit var mOptionTrueView: View
    private lateinit var mOptionFalseView: View

    override fun getLayoutId(): Int {
        return R.layout.true_false_question_fragment
    }

    override fun initView() {
        mText = rootView.findViewById(R.id.tv_text)
        mOptionTrue = rootView.findViewById(R.id.tv_option_true)
        mOptionFalse = rootView.findViewById(R.id.tv_option_false)
        mOptionTrueBackgroundView = rootView.findViewById(R.id.option_true)
        mOptionFalseBackgroundView = rootView.findViewById(R.id.option_false)
        mOptionTrueView = rootView.findViewById(R.id.rl_option_false)
        mOptionFalseView = rootView.findViewById(R.id.rl_option_false)
    }

    override fun prepareComponents() {
        // disable next question button
        mActivity.onNextQuestionButtonEnable(false)

        mOptionTrueBackgroundView.setOnClickListener {

            mOptionTrueBackgroundView.background =
                if (mQuestion.answer == "1")
                    ContextCompat.getDrawable(mActivity, R.drawable.answer_correct_option_background)
                else
                    ContextCompat.getDrawable(mActivity, R.drawable.answer_wrong_option_background)

            mOptionFalseBackgroundView.setOnClickListener(null)

            mQuestion.wasOK = mQuestion.answer == "1"
            mQuestion.answered = true
            mQuestion.chosenOption = "1"

            // enable next question button
            mActivity.onNextQuestionButtonEnable(true)
            mActivity.onOptionSelected(mQuestion)
        }
        mOptionFalseBackgroundView.setOnClickListener {

            mOptionFalseBackgroundView.background =
                if (mQuestion.answer == "0")
                    ContextCompat.getDrawable(mActivity, R.drawable.answer_correct_option_background)
                else
                    ContextCompat.getDrawable(mActivity, R.drawable.answer_wrong_option_background)

            mOptionTrueBackgroundView.setOnClickListener(null)

            mQuestion.wasOK = mQuestion.answer == "0"
            mQuestion.answered = true
            mQuestion.chosenOption = "0"

            // enable next question button
            mActivity.onNextQuestionButtonEnable(true)
            mActivity.onOptionSelected(mQuestion)
        }
        onUpdateQuestionView()
    }

    override fun onUpdateQuestionView() {
        mText.text = mQuestion.text
        mOptionTrue.text = getString(R.string.option_true_text)
        mOptionFalse.text = getString(R.string.option_false_text)
    }

    override fun showAnswer() {
        if (mQuestion.answer.isNotEmpty()) {
            when(mQuestion.answer) {
                "0" -> {
                    mOptionFalseBackgroundView.background =
                        ContextCompat.getDrawable(mActivity, R.drawable.answer_response_background)
                }
                "1" -> {
                    mOptionTrueBackgroundView.background =
                        ContextCompat.getDrawable(mActivity, R.drawable.answer_response_background)
                }
            }
            mQuestion.wasOK = false
            mQuestion.answered = false
            mQuestion.chosenOption = "-1"
        }
    }
}