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

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.jorzet.casmal.R
import com.jorzet.casmal.models.DifficultyType
import com.jorzet.casmal.models.Exam
import com.jorzet.casmal.models.PurchaseType

import kotlinx.android.synthetic.main.custom_exam_item.view.*

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

class ExamsAdapter(private val userIsPremium:Boolean, exams: List<Exam>): BaseAdapter() {
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
        fun onBecomePremium()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View

        view = if (convertView == null) {
            val layoutInflater = LayoutInflater.from(parent?.context)
            layoutInflater.inflate(R.layout.custom_exam_item, parent, false)
        } else {
            convertView
        }

        val exam = getItem(position)

        view.iv_exam_image.background =
            if (exam.purchaseType == PurchaseType.FREE) {
                ContextCompat.getDrawable(parent?.context!!, R.drawable.ic_exam)
            } else {
                if (userIsPremium) {
                    ContextCompat.getDrawable(parent?.context!!, R.drawable.ic_exam)
                } else {
                    ContextCompat.getDrawable(parent?.context!!, R.drawable.ic_exam_premium)
                }
            }

        when(exam.difficulty) {
            DifficultyType.EASY ->
                view.iv_difficulty.background = ContextCompat.getDrawable(parent.context!!, R.drawable.ic_easy)
            DifficultyType.MEDIUM ->
                view.iv_difficulty.background = ContextCompat.getDrawable(parent.context!!, R.drawable.ic_medium)
            DifficultyType.HARD ->
                view.iv_difficulty.background = ContextCompat.getDrawable(parent.context!!, R.drawable.ic_hard)
        }

        view.tv_exam.text =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                Html.fromHtml(exam.examName, Html.FROM_HTML_MODE_COMPACT)
            else
                Html.fromHtml(exam.examName)

        view.tv_exam.setTextColor(
        if (exam.purchaseType == PurchaseType.FREE) {
            ContextCompat.getColor(parent.context!!, R.color.black)
        } else {
            if (userIsPremium) {
                ContextCompat.getColor(parent.context!!, R.color.black)
            } else {
                ContextCompat.getColor(parent.context!!, R.color.gray)
            }
        })


        view.setOnClickListener {
            if (::mExamClickListener.isInitialized) {
                if (exam.purchaseType == PurchaseType.FREE || userIsPremium) {
                    mExamClickListener.onExamClick(exam)
                } else if (exam.purchaseType == PurchaseType.PREMIUM && !userIsPremium) {
                    mExamClickListener.onBecomePremium()
                }
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