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

package com.jorzet.casmal.fragments

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jorzet.casmal.R
import com.jorzet.casmal.adapters.SubjectsAdapter
import com.jorzet.casmal.base.BaseFragment
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.Subject

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 12/08/19.
 */

class SubjectsFragment: BaseFragment() {

    /**
     * UI accessors
     */
    private lateinit var mSubjectsRecyclerView: RecyclerView

    /**
     * Adapter
     */
    private lateinit var mSubjectsAdapter: SubjectsAdapter

    override fun getLayoutId(): Int {
        return R.layout.subjects_fragment
    }

    override fun initView() {
        mSubjectsRecyclerView = rootView.findViewById(R.id.rv_subjects)
    }

    override fun prepareComponents() {
        FirebaseRequestManager.getInstance(context!!).requestSubjects(object: FirebaseRequestManager.OnGetSubjectsListener {
            override fun onGetSubjectsSuccess(subjects: List<Subject>) {
                Log.d("","")

                mSubjectsRecyclerView.layoutManager = GridLayoutManager(context, 3)
                mSubjectsAdapter = SubjectsAdapter(context!!, subjects)
                mSubjectsAdapter.mSubjectClickListener = object: SubjectsAdapter.OnSubjectClickListener {
                    override fun onSubjectClick(subject: Subject) {
                        goQuestionActivity(subject.questions, false, null)
                    }
                }
                mSubjectsRecyclerView.adapter = mSubjectsAdapter
            }

            override fun onGetSubjectsFail(throwable: Throwable) {
                Log.d("","")
            }
        })
    }

}