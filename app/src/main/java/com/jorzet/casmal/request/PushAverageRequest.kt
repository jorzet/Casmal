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

package com.jorzet.casmal.request

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.jorzet.casmal.models.Average
import java.util.*
import kotlin.collections.HashMap

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 27/12/19.
 */

class PushAverageRequest(isExam: Boolean, average: Average): AbstractUpdateDatabase<Average, Boolean>() {
    /**
     * Constants
     */
    companion object {
        private const val TAG: String = "PushAverageRequest"
        private const val USERS_REFERENCE: String = "users"
        private const val TOTAL_QUESTIONS: String = "total_questions"
        private const val ANSWERED_QUESTIONS: String = "answered_questions"
        private const val CORRECT: String = "correct"
        private const val INCORRECT: String = "incorrect"
        private const val ANSWERED_EXAMS_PARAM: String = "answeredExams"
        private const val ANSWERED_QUESTIONS_PARAM: String = "answeredQuestions"
    }

    /**
     * Attributes
     */
    private val mIsExam: Boolean = isExam
    private val mAverage: Average = average

    override fun getReference(): String? {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            return USERS_REFERENCE + "/" + user.uid + "/" +
                    if (mIsExam) {
                        ANSWERED_EXAMS_PARAM + "/"  + mAverage.examId.toLowerCase(Locale.getDefault())
                    } else {
                        ANSWERED_QUESTIONS_PARAM + "/"  + mAverage.subjectType.name.toLowerCase(Locale.getDefault())
                    }
        }
        return null
    }

    override fun getParams(): HashMap<String, Any>? {
        val averageParams = HashMap<String, Any>()

        averageParams[TOTAL_QUESTIONS] = mAverage.totalQuestions
        averageParams[ANSWERED_QUESTIONS] = mAverage.answeredQuestions
        averageParams[CORRECT] = mAverage.correct
        averageParams[INCORRECT] = mAverage.incorrect

        return averageParams
    }

    override fun onUpdateSuccess() {
        Log.d(TAG,"question update success")
        onRequestListenerSuccess.onSuccess(true)
    }

    override fun onUpdateError(errorResponse: Throwable) {
        Log.d(TAG,"question update fail")
        onRequestListenerFailed.onFailed(Throwable())
    }
}