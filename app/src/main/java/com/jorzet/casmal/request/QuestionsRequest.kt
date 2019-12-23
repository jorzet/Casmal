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

package com.jorzet.casmal.request

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.jorzet.casmal.models.Question
import org.json.JSONObject

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 17/07/19.
 */

class QuestionsRequest(questionId: String): AbstractRequestDatabase<String, Question>() {

    companion object {
        /**
         * Constants
         */
        private const val TAG: String = "QuestionsTask"
        private const val QUESTIONS_REFERENCE: String = "questions"
    }

    /**
     * Attributes
     */
    private val mQuestionId: String = questionId

    override fun getReference(): String? {
        return "$QUESTIONS_REFERENCE/$mQuestionId"
    }

    override fun onGettingResponse(successResponse: DataSnapshot) {
        Log.d(TAG,"question request success")
        val post = successResponse.value
        if (post != null) {
            val questionMap = (post as HashMap<*, *>)
            val questionJson = JSONObject(questionMap).toString()
            try {
                val question = Gson().fromJson(questionJson, Question::class.java)
                onRequestListenerSuccess.onSuccess(question)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        } else {
            onRequestListenerFailed.onFailed(Throwable())
        }

    }

    override fun onGettingError(errorResponse: DatabaseError) {
        Log.d(TAG,"question request fail")
        onRequestListenerFailed.onFailed(Throwable())
    }
}