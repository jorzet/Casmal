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

package com.jorzet.casmal.managers

import android.annotation.SuppressLint
import android.content.Context
import com.jorzet.casmal.models.Module
import com.jorzet.casmal.models.Question
import com.jorzet.casmal.models.Subject
import com.jorzet.casmal.request.AbstractRequestDatabase
import com.jorzet.casmal.request.ModulesRequest
import com.jorzet.casmal.request.QuestionsRequest
import com.jorzet.casmal.request.SubjectsRequest

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 087/08/19.
 */

class FirebaseRequestManagerImp(context: Context): FirebaseRequestManager(context) {

    companion object {
        /**
         * Manager static instance
         */
        @SuppressLint("StaticFieldLeak")
        private var sInstance: FirebaseRequestManagerImp? = null

        /**
         * Creates a [FirebaseRequestManager] implementation instance
         *
         * @param context Base Activity or Fragment [Context]
         * @return A [FirebaseRequestManager] instance
         */
        fun getInstance(context: Context): FirebaseRequestManager {
            if (sInstance == null) {
                synchronized(FirebaseRequestManager::class.java) {
                    if (sInstance == null) {
                        sInstance = FirebaseRequestManagerImp(context)
                    }
                }
            }
            return sInstance!!
        }
    }

    override fun destroy() {
        sInstance = null
    }

    override fun requestQuestion(questionId: String, onGetQuestionsListener: OnGetQuestionListener) {
        val questionsRequest = QuestionsRequest(questionId)

        questionsRequest.setOnRequestSuccess(object: AbstractRequestDatabase.OnRequestListenerSuccess<Question> {
            override fun onSuccess(result: Question) {
                onGetQuestionsListener.onGetQuestionLoaded(result)
            }
        })

        questionsRequest.setOnRequestFailed(object: AbstractRequestDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onGetQuestionsListener.onGetQuestionError(throwable)
            }
        })

        questionsRequest.request()
    }

    override fun requestModules(onGetModulesListener: OnGetModulesListener) {
        val modulesRequest = ModulesRequest()

        modulesRequest.setOnRequestSuccess(object: AbstractRequestDatabase.OnRequestListenerSuccess<List<Module>> {
            override fun onSuccess(result: List<Module>) {
                onGetModulesListener.onGetModulesSuccess(result)
            }
        })

        modulesRequest.setOnRequestFailed(object: AbstractRequestDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onGetModulesListener.onGetModulesFail(throwable)
            }
        })

        modulesRequest.request()
    }

    override fun requestSubjects(onGetSubjectsListener: OnGetSubjectsListener) {
        val subjectsRequest = SubjectsRequest()

        subjectsRequest.setOnRequestSuccess(object: AbstractRequestDatabase.OnRequestListenerSuccess<List<Subject>> {
            override fun onSuccess(result: List<Subject>) {
                onGetSubjectsListener.onGetSubjectsSuccess(result)
            }
        })

        subjectsRequest.setOnRequestFailed(object: AbstractRequestDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onGetSubjectsListener.onGetSubjectsFail(throwable)
            }
        })

        subjectsRequest.request()
    }

}