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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.jorzet.casmal.R
import com.jorzet.casmal.adapters.ModulesAdapter
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.Module

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 12/08/19.
 */

class ModulesFragment: BaseFragment() {

    /**
     * UI accessors
     */
    private lateinit var mModulesListView: ListView

    /**
     * Adapter
     */
    private lateinit var mModulesAdapter: ModulesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.modules_fragment, container, false)

        mModulesListView = rootView.findViewById(R.id.lv_modules)

        FirebaseRequestManager.getInstance(context!!).requestModules(object: FirebaseRequestManager.OnGetModulesListener {
            override fun onGetModulesSuccess(modules: List<Module>) {
                Log.d("","")

                mModulesAdapter = ModulesAdapter(modules)
                mModulesAdapter.mModuleClickListener = object: ModulesAdapter.OnModuleClickListener {
                    override fun onModuleClick(module: Module) {
                        goQuestionActivity(module!!.questions)
                    }
                }

                mModulesListView.adapter = mModulesAdapter
            }

            override fun onGetModulesFail(throwable: Throwable) {
                Log.d("","")
            }
        })




        return rootView
    }
}