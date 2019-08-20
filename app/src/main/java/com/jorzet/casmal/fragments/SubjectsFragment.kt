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

package com.jorzet.casmal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.jorzet.casmal.R
import com.jorzet.casmal.adapters.SubjectsAdapter
import com.jorzet.casmal.models.Subject
import com.jorzet.casmal.models.SubjectType

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 12/08/19.
 */

class SubjectsFragment: BaseFragment() {

    /**
     * UI accessors
     */
    private lateinit var mSubjectsRecyclerView: GridView

    /**
     * Adapter
     */
    private lateinit var mSubjectsAdapter: SubjectsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.subjects_fragment, container, false)

        mSubjectsRecyclerView = rootView.findViewById(R.id.rv_subjects)

        // hardcode to show subjects
        val subjects = arrayListOf<Subject>()
        val subject1 = Subject("epidemiology", "Epidemiología", "s1", SubjectType.EPIDEMIOLOGY, 0.0)
        val subject2 = Subject("biochemistry", "Bioquimica", "s2", SubjectType.BIOCHEMISTRY, 0.0)
        val subject3 = Subject("neurology", "Neurología", "s3", SubjectType.NEUROLOGY, 0.0)
        val subject4 = Subject("", "", "s4", SubjectType.NONE, 0.0)

        subjects.add(subject1)
        subjects.add(subject2)
        subjects.add(subject3)
        (0..20).forEach { _ ->
            subjects.add(subject4)
        }

        mSubjectsAdapter = SubjectsAdapter(context!!, subjects)
        mSubjectsAdapter.mSubjectClickListener = object: SubjectsAdapter.OnSubjectClickListener {
            override fun onSubjectClick(subject: Subject) {
                goQuestionActivity()
            }
        }
        mSubjectsRecyclerView.adapter = mSubjectsAdapter

        return rootView
    }

}