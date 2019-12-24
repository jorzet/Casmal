package com.jorzet.casmal.fragments

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jorzet.casmal.R
import com.jorzet.casmal.adapters.QuestionsAdapter
import com.jorzet.casmal.base.BaseFragment
import com.jorzet.casmal.models.Question
import com.jorzet.casmal.ui.QuestionActivity

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

class QuestionListFragment: BaseFragment() {

    /**
     * UI accessors
     */
    private lateinit var mQuestionsList: RecyclerView
    private lateinit var mCloseQuestions: View

    private lateinit var questionsAdapter: QuestionsAdapter

    private var mSelectedQuestion: Int = 0
    private var mQuestionList: List<Question> = emptyList()

    companion object {
        fun getInstance(questionList: List<Question>, selectedQuestion: Int): QuestionListFragment{
            val questionListFragment = QuestionListFragment()
            questionListFragment.mSelectedQuestion = selectedQuestion
            questionListFragment.mQuestionList = questionList
            return questionListFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.question_list_fragment
    }

    override fun initView() {
        mQuestionsList = rootView.findViewById(R.id.rv_question_list)
        mCloseQuestions = rootView.findViewById(R.id.iv_close_questions)
    }

    override fun prepareComponents() {
        mCloseQuestions.setOnClickListener {
            activity!!.onBackPressed()
        }

        mQuestionsList.layoutManager = GridLayoutManager(context, 5)
        mQuestionsList.adapter = QuestionsAdapter(mQuestionList, mSelectedQuestion, object: QuestionsAdapter.OnQuestionSelectedListener {
            override fun onQuestionSelected(questionId: String, position: Int) {
                (activity as QuestionActivity).onChangeQuestion(questionId, position)
                activity!!.onBackPressed()
            }
        })
    }
}