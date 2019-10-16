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

import android.app.Activity
import android.content.Context
import com.jorzet.casmal.models.Question

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 087/08/19.
 */

abstract class FirebaseRequestManager(activity: Activity) {

    protected val TAG : String = "FirebaseRequestManager"
    protected val mActivity: Activity = activity

    companion object {
        /**
         * Manager constructor
         * @param activity Base Activity or Fragment [Context]
         */
        fun getInstance(activity: Activity): FirebaseRequestManager {
            return FirebaseRequestManagerImp.getInstance(activity)
        }
    }

    interface OnGetQuestionsListener {
        /**
         *
         */
        fun onGetQuestionLoaded(question: Question)

        /**
         *
         */
        fun onGetQuestionError(throwable: Throwable)
    }

    /**
     *
     */
    abstract fun requestQuestion(questionId: String, onGetQuestionsListener: OnGetQuestionsListener)

    /**
     * Destroy [FirebaseRequestManager] instance
     */
    abstract fun destroy()

}