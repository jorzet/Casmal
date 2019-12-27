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
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseActivity
import com.jorzet.casmal.base.BaseQuestionFragment
import com.jorzet.casmal.fragments.QuestionListFragment
import com.jorzet.casmal.fragments.question.MatchQuestionFragment
import com.jorzet.casmal.fragments.question.MultipleQuestionFragment
import com.jorzet.casmal.fragments.question.TrueFalseQuestionFragment
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.Average
import com.jorzet.casmal.models.Question
import com.jorzet.casmal.models.QuestionType

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

class QuestionActivity: BaseActivity(), BaseQuestionFragment.OnOptionSelectedListener {
    /**
     * Tags
     */
    companion object {
        const val QUESTION_LIST: String = "question_list"
        const val IS_EXAM: String = "is_exam"
        const val MODULE_ID: String = "module_id"
        const val TAG_FRAGMENT_QUESTIONS: String = "questions_list_fragment"
    }

    /**
     * UI accessors
     */
    private lateinit var mQuestionTitle: TextView
    private lateinit var mShowQuestions: View
    private lateinit var mCloseQuestions: View
    private lateinit var mProgresBarQuestions: ProgressBar
    private lateinit var mShowAnswer: View
    private lateinit var mNextQuestion: View
    private lateinit var mLoadingQuestionProgressBar: FrameLayout

    /**
     * Models
     */
    private var mQuestions: List<String>? = arrayListOf()
    private var mQuestionsList: ArrayList<Question> = arrayListOf()
    private lateinit var mAverage: Average

    /**
     * Attributes
     */
    private var mCurrectQuestionIndex = 0
    private var mCurrentQuestionProgress = 0
    private var mIsExam: Boolean = false
    private var mModuleId: String = ""

    /**
     * Fragment
     */
    private lateinit var currentFragment: BaseQuestionFragment

    override fun getLayoutId(): Int {
        return R.layout.activity_question
    }

    override fun initView() {
        mQuestionTitle = findViewById(R.id.tv_question_type_text)
        mShowQuestions = findViewById(R.id.rl_show_questions)
        mCloseQuestions = findViewById(R.id.iv_close_question)
        mProgresBarQuestions = findViewById(R.id.pb_questions_progress)
        mNextQuestion = findViewById(R.id.btn_next_question)
        mShowAnswer = findViewById(R.id.btn_show_answer)
        mLoadingQuestionProgressBar = findViewById(R.id.progressBarHolder)
    }

    override fun prepareComponents() {
        mShowQuestions.setOnClickListener(mShowQuestionsClickListener)
        mCloseQuestions.setOnClickListener(mCloseQuestionsClickListener)
        mNextQuestion.setOnClickListener(mNextQuestionClickListener)
        mShowAnswer.setOnClickListener(mShowAnswerClickListener)

        mQuestions = intent.extras!!.getStringArrayList(QUESTION_LIST)
        mIsExam = intent.extras!!.getBoolean(IS_EXAM)
        mModuleId = intent.extras!!.getString(MODULE_ID, "")

        if (mQuestions != null) {
            for (question in mQuestions!!) {
                val mQuestion = Question()
                mQuestion.questionId = question
                mQuestionsList.add(mQuestion)
            }
        }

        mAverage = Average()
        mAverage.totalQuestions = mQuestionsList.size

        mLoadingQuestionProgressBar.visibility = View.VISIBLE
        if (mQuestions != null && mCurrectQuestionIndex < mQuestions?.size!!) {
            val question = mQuestions?.get(mCurrectQuestionIndex)
            onChangeQuestion(question, mCurrectQuestionIndex)
        }
    }

    override fun onBackPressed() {
        val fragment: Fragment? = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_QUESTIONS)
        if (fragment != null) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        } else {
            super.onBackPressed()
        }
    }

    private val mShowQuestionsClickListener = View.OnClickListener {
        val questionListFragment = QuestionListFragment.getInstance(mQuestionsList, mCurrectQuestionIndex)

        // add to fragment manager
        supportFragmentManager
            .beginTransaction()
            .add(R.id.complete_question_fragment_container, questionListFragment, TAG_FRAGMENT_QUESTIONS)
            .commitAllowingStateLoss()
    }

    private val mCloseQuestionsClickListener = View.OnClickListener {
        onBackPressed()
    }

    private val mNextQuestionClickListener = View.OnClickListener {
        if (mQuestions != null && mCurrectQuestionIndex < mQuestions?.size!!) {
            val question = mQuestions?.get(mCurrectQuestionIndex)
            onChangeQuestion(question, mCurrectQuestionIndex)
        }
    }

    private val mShowAnswerClickListener = View.OnClickListener {
        if (::currentFragment.isInitialized) {
            currentFragment.showAnswer()
        }
    }

    /**
     *
     */
    public fun onChangeQuestion(question: String?, position: Int) {
        if (::currentFragment.isInitialized) {
            currentFragment.onPushAverage(mAverage, mIsExam)
        }

        if (question != null) {
            FirebaseRequestManager.getInstance(this).requestQuestion(
                question,
                object : FirebaseRequestManager.OnGetQuestionListener {
                    override fun onGetQuestionLoaded(question: Question) {
                        Log.d("", "")

                        mLoadingQuestionProgressBar.visibility = View.GONE
                        mQuestionTitle.text = question.subject.value

                        // instance question fragment
                        when (question.questionType) {
                            QuestionType.MULTIPLE -> {
                                currentFragment =
                                    MultipleQuestionFragment(question,
                                        this@QuestionActivity)
                            }
                            QuestionType.TRUE_FALSE -> {
                                currentFragment =
                                    TrueFalseQuestionFragment(question,
                                        this@QuestionActivity)
                            }
                            QuestionType.MATCH -> {
                                currentFragment =
                                    MatchQuestionFragment(question,
                                        this@QuestionActivity)
                            }
                            else -> {}
                        }

                        onButtonsEnable()

                        if (mIsExam) {
                            mAverage.moduleId = mModuleId
                        } else {
                            mAverage.subjectType = question.subject
                        }

                        // add to fragment manager
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.question_fragment_container, currentFragment)
                            .commitAllowingStateLoss()

                    }

                    override fun onGetQuestionError(throwable: Throwable) {
                        Log.d("", "")
                        mLoadingQuestionProgressBar.visibility = View.GONE
                    }
                })

            // increase questionIndex
            mCurrectQuestionIndex = position + 1

            // update progress
            if (mQuestions != null) {
                mCurrentQuestionProgress = (mCurrectQuestionIndex * 100) / mQuestions?.size!!
                mProgresBarQuestions.progress = mCurrentQuestionProgress
            }
        }

    }

    override fun onButtonsEnable() {
        if (::mShowQuestions.isInitialized) mShowQuestions.isEnabled = true
        if (::mCloseQuestions.isInitialized) mCloseQuestions.isEnabled = true
        if (::mShowAnswer.isInitialized) mShowAnswer.isEnabled = true
        if (::mNextQuestion.isInitialized) mNextQuestion.isEnabled = true
    }

    override fun onNextQuestionButtonEnable(enable: Boolean) {
        if (::mNextQuestion.isInitialized) {
            mNextQuestion.isEnabled = enable
        }
    }

    override fun onOptionSelected(question: Question) {
        if (::mShowAnswer.isInitialized) {
            mShowAnswer.isEnabled = !question.answered
        }

        if (mQuestionsList.isNotEmpty()) {
            mQuestionsList[mCurrectQuestionIndex - 1] = question
        }

        if (::mAverage.isInitialized) {
            mAverage.answeredQuestions++

            if (question.wasOK) mAverage.correct++
            else mAverage.incorrect++
        }
    }
}