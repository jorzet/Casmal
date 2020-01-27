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

package com.jorzet.casmal.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
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

    /**
     * This method returns custom [Fragment] layout resource
     * @return dialog layout resource
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * This method is to init each [View] component with rootView attribute
     */
    abstract fun initView()

    /**
     * This method is just to set data, listeners or handle [View] components
     */
    abstract fun prepareComponents()

    /**
     * This method creates an intent to start [QuestionActivity]
     */
    fun goQuestionActivity(questions: List<String>, isExam: Boolean, examId: String?) {
        val intent = Intent(activity, QuestionActivity::class.java)
        intent.putStringArrayListExtra(QuestionActivity.QUESTION_LIST, questions as ArrayList<String>)
        intent.putExtra(QuestionActivity.IS_EXAM, isExam)
        if (examId != null) intent.putExtra(QuestionActivity.EXAM_ID, examId)
        startActivity(intent)
    }
}