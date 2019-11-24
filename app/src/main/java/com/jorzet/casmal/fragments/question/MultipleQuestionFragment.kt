package com.jorzet.casmal.fragments.question

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jorzet.casmal.R
import com.jorzet.casmal.fragments.BaseFragment
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.Question
import com.jorzet.casmal.ui.QuestionActivity

class MultipleQuestionFragment: BaseFragment() {

    private lateinit var mText: TextView
    private lateinit var mOptionA: TextView
    private lateinit var mOptionB: TextView
    private lateinit var mOptionC: TextView
    private lateinit var mOptionD: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (container == null)
            return null

        val rootView = inflater.inflate(R.layout.multiple_question_fragment, container, false)!!


        mText = rootView.findViewById(R.id.tv_text)
        mOptionA = rootView.findViewById(R.id.tv_option_a)
        mOptionB = rootView.findViewById(R.id.tv_option_b)
        mOptionC = rootView.findViewById(R.id.tv_option_c)
        mOptionD = rootView.findViewById(R.id.tv_option_d)

        (activity as QuestionActivity).mNextQuestion.setOnClickListener {
            val question = (activity as QuestionActivity).mQuestions?.get((activity as QuestionActivity).mCurrectQuestionIndex)

            if (question != null) {
                FirebaseRequestManager.getInstance(context!!).requestQuestion(
                    question,
                    object : FirebaseRequestManager.OnGetQuestionListener {
                        override fun onGetQuestionLoaded(question: Question) {
                            Log.d("", "")

                            mText.text = question.text
                            mOptionA.text = question.opt1
                            mOptionB.text = question.opt2
                            mOptionC.text = question.opt3
                            mOptionD.text = question.opt4
                        }

                        override fun onGetQuestionError(throwable: Throwable) {
                            Log.d("", "")
                        }
                    })
            }

            (activity as QuestionActivity).mCurrectQuestionIndex = (activity as QuestionActivity).mCurrectQuestionIndex + 1
        }

        val question = (activity as QuestionActivity).mQuestions?.get((activity as QuestionActivity).mCurrectQuestionIndex)

        if (question != null) {
                FirebaseRequestManager.getInstance(context!!).requestQuestion(question, object: FirebaseRequestManager.OnGetQuestionListener {
                override fun onGetQuestionLoaded(question: Question) {
                    Log.d("","")

                    mText.text = question.text
                    mOptionA.text = question.opt1
                    mOptionB.text = question.opt2
                    mOptionC.text = question.opt3
                    mOptionD.text = question.opt4
                }
                override fun onGetQuestionError(throwable: Throwable) {
                    Log.d("","")
                }
            })
            (activity as QuestionActivity).mCurrectQuestionIndex = (activity as QuestionActivity).mCurrectQuestionIndex + 1
        }


        return rootView;
    }

}