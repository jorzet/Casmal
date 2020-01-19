/*
 * Copyright [2020] [Jorge Zepeda Tinoco]
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
import androidx.lifecycle.ViewModelProviders
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseActivity
import com.jorzet.casmal.base.BaseQuestionFragment
import com.jorzet.casmal.dialogs.LevelUpDialog
import com.jorzet.casmal.fragments.QuestionListFragment
import com.jorzet.casmal.fragments.question.MatchQuestionFragment
import com.jorzet.casmal.fragments.question.MultipleQuestionFragment
import com.jorzet.casmal.fragments.question.TrueFalseQuestionFragment
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.managers.ServiceManager
import com.jorzet.casmal.models.*
import com.jorzet.casmal.utils.Utils
import com.jorzet.casmal.viewmodels.FlashCardsViewModel
import com.jorzet.casmal.viewmodels.LevelsViewModel

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

class QuestionActivity: BaseActivity(), BaseQuestionFragment.OnOptionSelectedListener,
    BaseQuestionFragment.OnLevelUpListener,  LevelUpDialog.OnOkButtonListener {
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
    private lateinit var mProgressBarQuestions: ProgressBar
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
    private var mCorrectQuestionIndex = 0
    private var mCurrentQuestionProgress = 0
    private var mIsExam: Boolean = false
    private var mModuleId: String = ""

    /**
     * Fragment
     */
    private lateinit var currentFragment: BaseQuestionFragment

    /**
     * ViewModels
     */
    private var flashCardsViewModel: FlashCardsViewModel? = null
    private var levelsViewModel: LevelsViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_question
    }

    override fun initView() {
        mQuestionTitle = findViewById(R.id.tv_question_type_text)
        mShowQuestions = findViewById(R.id.rl_show_questions)
        mCloseQuestions = findViewById(R.id.iv_close_question)
        mProgressBarQuestions = findViewById(R.id.pb_questions_progress)
        mNextQuestion = findViewById(R.id.btn_next_question)
        mShowAnswer = findViewById(R.id.btn_show_answer)
        mLoadingQuestionProgressBar = findViewById(R.id.progressBarHolder)
    }

    override fun prepareComponents() {
        flashCardsViewModel = ViewModelProviders.of(this).get(FlashCardsViewModel::class.java)
        levelsViewModel = ViewModelProviders.of(this).get(LevelsViewModel::class.java)

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
        if (mQuestions != null && mCorrectQuestionIndex < mQuestions?.size!!) {
            val question = mQuestions?.get(mCorrectQuestionIndex)
            onChangeQuestion(question, mCorrectQuestionIndex)
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
        val questionListFragment = QuestionListFragment.getInstance(mQuestionsList, mCorrectQuestionIndex)

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
        if (mQuestions != null && mCorrectQuestionIndex < mQuestions?.size!!) {
            val question = mQuestions?.get(mCorrectQuestionIndex)
            onChangeQuestion(question, mCorrectQuestionIndex)
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
    fun onChangeQuestion(question: String?, position: Int) {
        if (::currentFragment.isInitialized) {
            currentFragment.onPushAverage(mAverage, mIsExam)
        }

        if (question != null) {
            FirebaseRequestManager.getInstance().requestQuestion(
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
            mCorrectQuestionIndex = position + 1

            // update progress
            if (mQuestions != null) {
                mCurrentQuestionProgress = (mCorrectQuestionIndex * 100) / mQuestions?.size!!
                mProgressBarQuestions.progress = mCurrentQuestionProgress
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
        val levels = levelsViewModel?.levels?.value ?: return

        if (::mShowAnswer.isInitialized) {
            mShowAnswer.isEnabled = !question.wasOK
        }

        if (mQuestionsList.isNotEmpty()) {
            mQuestionsList[mCorrectQuestionIndex - 1] = question
        }

        if (::mAverage.isInitialized) {
            mAverage.answeredQuestions++

            if (question.wasOK) mAverage.correct++
            else mAverage.incorrect++
        }

        val user = ServiceManager.getInstance().user
        if (user != null && question.wasOK) {
            Utils.print("UserPoints: ${user.points}")

            user.points += question.points.toInt()

            Utils.print("PointsAdded: ${question.points}")

            Utils.print("Total Levels in repository: ${levels.size}")

            val userLevel: Level = if(user.level - 1 < 0) {
                levels[0]
            } else {
                levels[user.level - 1]
            }

            Utils.print("NextLevel: ${userLevel.id} with ${userLevel.points} points and id = $userLevel")

            Utils.print("CurrentLevel: ${user.level}")
            Utils.print("UserPoints: ${user.points}")

            if(user.points >= userLevel.points) {
                val newLevel = userLevel.id + 1
                Utils.print("NewLevel: $newLevel")

                user.level = newLevel
                onLevelUp(levels[newLevel])
            }
        }
    }

    override fun onLevelUp(level: Level) {
        Utils.print("OnLevelUp!: ${level.id}")

        val user = ServiceManager.getInstance().user
        val flashCardGotIt = getFlashCard(level)
        if (user != null && flashCardGotIt != null) {

            // show level up dialog
            LevelUpDialog.newInstance(
                user.level,
                flashCardGotIt,
                this).show(supportFragmentManager, "level_up_dialog")

            // update flashcards
            val flashCards: ArrayList<String> = arrayListOf()
            flashCards.addAll(user.flashCards)
            flashCards.add(level.flashcard)
            user.flashCards = flashCards

            // update user flash cards
            val userFlashCards: ArrayList<FlashCard> = arrayListOf()
            userFlashCards.addAll(ServiceManager.getInstance().userFlashCards)
            userFlashCards.add(FlashCard(level.flashcard, flashCardGotIt))
            ServiceManager.getInstance().userFlashCards = userFlashCards

            // update user level
            FirebaseRequestManager.getInstance().updateUserLevel(object: FirebaseRequestManager.OnUpdateUserLevelListener {
                override fun onUpdateUserLevelSuccess() {
                    Utils.print("QuestionActivity update level success")
                }

                override fun onUpdateUserLevelFail(throwable: Throwable) {
                    Utils.print("QuestionActivity update level fail")
                }
            })
        }
    }

    override fun getFlashCard(level: Level): String? {
        Utils.print("getFlashCard Level: ${level.id}")

        val flashCards = flashCardsViewModel?.flashCards?.value ?: return null

        flashCards.forEach {
            if(it.id == level.flashcard) {
                return it.storageName
            }
        }

        return null
    }

    override fun onOkButtonClick() {

    }
}