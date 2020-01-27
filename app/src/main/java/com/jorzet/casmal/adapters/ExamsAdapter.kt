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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.jorzet.casmal.R
import com.jorzet.casmal.models.DifficultyType
import com.jorzet.casmal.models.Exam

import kotlinx.android.synthetic.main.custom_exam_item.view.*

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

class ExamsAdapter(exams: List<Exam>): BaseAdapter() {
    /**
     * Model
     */
    private val mExams = exams

    /**
     * Attributes
     */
    lateinit var mExamClickListener: OnExamClickListener

    interface OnExamClickListener {
        fun onExamClick(exam: Exam)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View

        view = if (convertView == null) {
            val layoutInflater = LayoutInflater.from(parent?.context)
            layoutInflater.inflate(R.layout.custom_exam_item, parent, false)
        } else {
            convertView
        }

        val module = getItem(position)

        view.iv_exam_image.background = ContextCompat.getDrawable(parent?.context!!, R.drawable.ic_exam)

        when(module.difficulty) {
            DifficultyType.EASY ->
                view.iv_difficulty.background = ContextCompat.getDrawable(parent.context!!, R.drawable.ic_easy)
            DifficultyType.MEDIUM ->
                view.iv_difficulty.background = ContextCompat.getDrawable(parent.context!!, R.drawable.ic_medium)
            DifficultyType.HARD ->
                view.iv_difficulty.background = ContextCompat.getDrawable(parent.context!!, R.drawable.ic_hard)
        }

        view.tv_exam.text = module.examName

        view.setOnClickListener {
            if (::mExamClickListener.isInitialized) {
                mExamClickListener.onExamClick(module)
            }
        }

        return view
    }

    override fun getItem(position: Int): Exam {
        return mExams[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return mExams.size
    }
}