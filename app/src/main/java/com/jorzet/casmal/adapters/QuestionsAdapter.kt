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

package com.jorzet.casmal.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jorzet.casmal.R
import com.jorzet.casmal.models.Question

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

class QuestionsAdapter(questions: List<Question>, selectedQuestion: Int,
                       onQuestionSelectedListener: OnQuestionSelectedListener):
    RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    private lateinit var mContext: Context
    private val mSelectedQuestion: Int = selectedQuestion
    private val mQuestions: List<Question> = questions
    private val mOnQuestionSelectedListener : OnQuestionSelectedListener = onQuestionSelectedListener

    interface OnQuestionSelectedListener {
        fun onQuestionSelected(questionId : String, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_question_item, parent, false)
        return QuestionViewHolder(view)
    }

    override fun getItemCount(): Int {
       return mQuestions.size
    }

    private fun getItem(position: Int): Question? {
        if (mQuestions.isNotEmpty() && position < mQuestions.size)
            return mQuestions[position]
        return null
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = getItem(position)
        if (question != null) {
                holder.view.background = ContextCompat.getDrawable(mContext,
                    if (question.answered) {
                        if (question.wasOK)
                            R.drawable.ok_question_answered_background
                        else
                            R.drawable.wrong_question_answered_background
                    } else {
                        if (position == (mSelectedQuestion - 1))
                            R.drawable.selected_question_background
                        else
                            R.drawable.unselected_question_background
                    })

            holder.textNumber.text = (position + 1).toString()

            holder.view.setOnClickListener {
                mOnQuestionSelectedListener.onQuestionSelected(question.questionId, position)
            }
        }
    }

    class QuestionViewHolder(v: View): RecyclerView.ViewHolder(v) {
        var view: View = v.findViewById(R.id.rl_background)
        var textNumber: AppCompatTextView = v.findViewById(R.id.tv_number)
    }
}