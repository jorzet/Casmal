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

package com.jorzet.casmal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.jorzet.casmal.R
import com.jorzet.casmal.models.Subject
import com.jorzet.casmal.models.SubjectType

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 16/08/19.
 */

class SubjectsAdapter(subjects: List<Subject>): BaseAdapter() {

    /**
     * Model
     */
    private val mSubjectList = subjects

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(parent?.context)
            view = layoutInflater.inflate(R.layout.custom_subject_item, parent, false)

        } else {
            view = convertView
        }

        if (position%2 == 0) {
            val subject = getItem(position/2) as Subject

            when(subject.subjectType) {
                SubjectType.NEUROLOGY -> {

                }
                SubjectType.BIOCHEMISTRY -> {

                }
                SubjectType.EPIDEMIOLOGY -> {

                }
                else -> {

                }
            }
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return mSubjectList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return mSubjectList.size + mSubjectList.size - 1
    }

}