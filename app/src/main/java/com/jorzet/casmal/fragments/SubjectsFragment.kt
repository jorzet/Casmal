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

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jorzet.casmal.R
import com.jorzet.casmal.adapters.SubjectsAdapter
import com.jorzet.casmal.base.BaseFragment
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.Subject
import com.jorzet.casmal.ui.MainActivity
import com.jorzet.casmal.ui.PaywayActivity
import com.jorzet.casmal.viewmodels.UserViewModel

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

    /**
     * View Model
     */
    private lateinit var userViewModel: UserViewModel

    override fun getLayoutId(): Int {
        return R.layout.subjects_fragment
    }

    override fun initView() {
        mSubjectsRecyclerView = rootView.findViewById(R.id.rv_subjects)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun prepareComponents() {
        val user = userViewModel.getUser().value

        FirebaseRequestManager.getInstance().requestSubjects(object: FirebaseRequestManager.OnGetSubjectsListener {
            override fun onGetSubjectsSuccess(subjects: List<Subject>) {
                mSubjectsRecyclerView.layoutManager = GridLayoutManager(context, 3)
                mSubjectsAdapter = SubjectsAdapter(context!!, user?.payment?.isPremium == true, subjects)
                mSubjectsAdapter.mSubjectClickListener = object: SubjectsAdapter.OnSubjectClickListener {
                    override fun onSubjectClick(subject: Subject) {
                        goQuestionActivity(subject.questions, false, null)
                    }

                    override fun onBecomePremium() {
                        val intent = Intent(activity, PaywayActivity::class.java)
                        startActivityForResult(intent, MainActivity.PREMIUM_RESULT_CODE)
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