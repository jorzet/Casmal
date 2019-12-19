package com.jorzet.casmal.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jorzet.casmal.ui.QuestionActivity

abstract class BaseFragment: Fragment() {
    protected lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(getLayoutId(), container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        prepareComponents()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun getFragmentActivity(): FragmentActivity
    abstract fun initView()
    abstract fun prepareComponents()

    /**
     * This method creates an intent to start [QuestionActivity]
     */
    fun goQuestionActivity(questions: List<String>, isExam: Boolean) {
        val intent = Intent(activity, QuestionActivity::class.java)
        intent.putStringArrayListExtra(QuestionActivity.QUESTION_LIST, questions as ArrayList<String>)
        intent.putExtra(QuestionActivity.IS_EXAM, isExam)
        startActivity(intent)
    }
}