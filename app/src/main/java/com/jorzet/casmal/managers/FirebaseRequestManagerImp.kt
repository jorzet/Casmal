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
import android.app.Activity
import android.content.Context
import com.jorzet.casmal.models.Question
import com.jorzet.casmal.request.AbstractRequestDatabase
import com.jorzet.casmal.request.QuestionsRequest

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 087/08/19.
 */

class FirebaseRequestManagerImp(activity: Activity): FirebaseRequestManager(activity) {


    companion object {
        /**
         * Manager static instance
         */
        @SuppressLint("StaticFieldLeak")
        private var sInstance: FirebaseRequestManagerImp? = null

        /**
         * Creates a [FirebaseRequestManager] implementation instance
         *
         * @param activity Base Activity or Fragment [Context]
         * @return A [FirebaseRequestManager] instance
         */
        fun getInstance(activity: Activity): FirebaseRequestManager {
            if (sInstance == null) {
                synchronized(FirebaseRequestManager::class.java) {
                    if (sInstance == null) {
                        sInstance = FirebaseRequestManagerImp(activity)
                    }
                }
            }
            return sInstance!!
        }
    }

    override fun destroy() {
        sInstance = null
    }

    override fun requestQuestion(questionId: String, onGetQuestionsListener: OnGetQuestionsListener) {
        val questionsTask = QuestionsRequest(questionId)

        questionsTask.setOnRequestSuccess(object: AbstractRequestDatabase.OnRequestListenerSuccess<Question> {
            override fun onSuccess(result: Question) {
                onGetQuestionsListener.onGetQuestionLoaded(result)
            }
        })

        questionsTask.setOnRequestFailed(object: AbstractRequestDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onGetQuestionsListener.onGetQuestionError(throwable)
            }
        })

        questionsTask.request()
    }

}