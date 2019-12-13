/*
 * Copyright [2019] [Jorge Zepeda Tinoco]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jorzet.casmal.ui

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseActivity
import com.jorzet.casmal.base.BaseQuestionFrgament
import com.jorzet.casmal.fragments.question.MultipleQuestionFragment
import com.jorzet.casmal.fragments.question.TrueFalseQuestionFragment
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.Question
import com.jorzet.casmal.models.QuestionType

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

class QuestionActivity: BaseActivity() {
    /**
     * Tags
     */
    companion object {
        const val QUESTION_LIST: String = "question_list"
    }

    /**
     * Models
     */
    private lateinit var mNextQuestion: View
    lateinit var mShowQuestions: View
    lateinit var mShowAnswer: View
    private var mQuestions: List<String>? = arrayListOf()
    private var mCurrectQuestionIndex = 0

    /**
     * Fragment
     */
    private lateinit var currentFragment: BaseQuestionFrgament

    override fun getLayoutId(): Int {
        return R.layout.activity_question
    }

    override fun getActivity(): FragmentActivity {
        return this
    }

    override fun initView() {
        mShowQuestions = findViewById(R.id.rl_show_questions)
        mNextQuestion = findViewById(R.id.btn_next_question)
        mShowAnswer = findViewById(R.id.btn_show_answer)
    }

    override fun prepareComponents() {
        mShowQuestions.setOnClickListener(mShowQuestionsClickListener)
        mNextQuestion.setOnClickListener(mNextQuestionClickListener)
        mShowAnswer.setOnClickListener(mShowAnswerClickListener)

        mQuestions = intent.extras!!.getStringArrayList(QUESTION_LIST)


        val question = mQuestions?.get(mCurrectQuestionIndex)
        if (question != null) {
            FirebaseRequestManager.getInstance(this).requestQuestion(
                question,
                object : FirebaseRequestManager.OnGetQuestionListener {
                    override fun onGetQuestionLoaded(question: Question) {
                        Log.d("", "")
                        if (question.questionType.equals(QuestionType.MULTIPLE)) {
                            currentFragment = MultipleQuestionFragment(question)
                        } else if (question.questionType.equals(QuestionType.TRUE_FALSE)) {
                            currentFragment = TrueFalseQuestionFragment(question)
                        }

                        val manager = getSupportFragmentManager();
                        val transaction = manager.beginTransaction();
                        transaction.replace(R.id.question_fragment_container, currentFragment);
                        transaction.commitAllowingStateLoss()
                    }

                    override fun onGetQuestionError(throwable: Throwable) {
                        Log.d("", "")
                    }
                })
            mCurrectQuestionIndex += 1
        }
    }

    private val mShowQuestionsClickListener = View.OnClickListener {

    }

    private val mNextQuestionClickListener = View.OnClickListener {
        val question = mQuestions?.get(mCurrectQuestionIndex)
        if (question != null) {
            FirebaseRequestManager.getInstance(this).requestQuestion(
                question,
                object : FirebaseRequestManager.OnGetQuestionListener {
                    override fun onGetQuestionLoaded(question: Question) {
                        Log.d("", "")
                        if (question.questionType.equals(QuestionType.MULTIPLE)) {
                            currentFragment = MultipleQuestionFragment(question)
                        } else if (question.questionType.equals(QuestionType.TRUE_FALSE)) {
                            currentFragment = TrueFalseQuestionFragment(question)
                        }

                        val manager = getSupportFragmentManager();
                        val transaction = manager.beginTransaction();
                        transaction.replace(R.id.question_fragment_container, currentFragment);
                        transaction.commitAllowingStateLoss()
                    }

                    override fun onGetQuestionError(throwable: Throwable) {
                        Log.d("", "")
                    }
                })
            mCurrectQuestionIndex += 1
        }
    }

    private val mShowAnswerClickListener = View.OnClickListener {

    }

}