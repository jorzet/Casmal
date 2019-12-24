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

import android.content.Context
import com.jorzet.casmal.models.Module
import com.jorzet.casmal.models.Question
import com.jorzet.casmal.models.Subject
import com.jorzet.casmal.models.User

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 087/08/19.
 */

abstract class FirebaseRequestManager(context: Context) {
    protected val TAG : String = "FirebaseRequestManager"
    protected val mContext: Context = context

    companion object {
        /**
         * Manager constructor
         * @param activity Base Activity or Fragment [Context]
         */
        fun getInstance(context: Context): FirebaseRequestManager {
            return FirebaseRequestManagerImp.getInstance(context)
        }
    }

    interface OnGetModulesListener {
        /**
         *
         */
        fun onGetModulesSuccess(modules: List<Module>)

        /**
         *
         */
        fun onGetModulesFail(throwable: Throwable)
    }

    interface OnGetSubjectsListener {
        /**
         *
         */
        fun onGetSubjectsSuccess(subjects: List<Subject>)

        /**
         *
         */
        fun onGetSubjectsFail(throwable: Throwable)
    }

    interface OnGetQuestionListener {
        /**
         *
         */
        fun onGetQuestionLoaded(question: Question)

        /**
         *
         */
        fun onGetQuestionError(throwable: Throwable)
    }

    interface OnGetUserListener {
        /**
         *
         */
        fun onGetUserLoaded(user: User?)
        /**
         *
         */
        fun onGetUserError(throwable: Throwable)
    }

    interface OnInsertUserListener {
        /**
         *
         */
        fun onSuccessUserInserted()
        /**
         *
         */
        fun onErrorUserInserted(throwable: Throwable)
    }

    interface OnPushQuestionListener {
        /**
         *
         */
        fun onPushQuestionSuccess()

        /**
         *
         */
        fun onPushQuestionFail(throwable: Throwable)
    }

    /**
     *
     */
    abstract fun requestModules(onGetModulesListener: OnGetModulesListener)

    /**
     *
     */
    abstract fun requestSubjects(onGetSubjectsListener: OnGetSubjectsListener)

    /**
     *
     */
    abstract fun requestQuestion(questionId: String, onGetQuestionsListener: OnGetQuestionListener)

    /**
     *
     */
    abstract fun requestUser(uid: String, onGetUserListener: OnGetUserListener)

    /**
     *
     */
    abstract fun insertUser(uid: String, onGetUserListener: OnInsertUserListener)

    /**
     *
     */
    abstract fun pushQuestion(isExam: Boolean, question: Question, onPushQuestionListener: OnPushQuestionListener)

    /**
     * Destroy [FirebaseRequestManager] instance
     */
    abstract fun destroy()
}