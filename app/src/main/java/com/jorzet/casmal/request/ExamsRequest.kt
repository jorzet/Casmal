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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.jorzet.casmal.models.Exam
import org.json.JSONObject
import java.util.*

class ExamsRequest: AbstractRequestDatabase<String, List<Exam>>() {

    companion object {
        /**
         * Constants
         */
        private const val TAG: String = "ExamsRequest"
        private const val MODULES_REFERENCE: String = "exams"
    }

    override fun getReference(): String? {
        return MODULES_REFERENCE
    }

    override fun onGettingResponse(successResponse: DataSnapshot) {
        Log.d(TAG,"exams request success")
        val post = successResponse.value
        if (post != null) {
            val examsMap = (post as HashMap<*, *>)
            val mExams = ArrayList<Exam>()
            for (key in examsMap.keys) {
                val examMap = examsMap[key] as HashMap<*, *>
                try {
                    val exam = Gson().fromJson(JSONObject(examMap).toString(), Exam::class.java)

                    // just save enabled exam
                    if (exam.enabled) {
                        mExams.add(exam)
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

            onRequestListenerSuccess.onSuccess(mExams)
        } else {
            onRequestListenerFailed.onFailed(Throwable())
        }
    }

    override fun onGettingError(errorResponse: DatabaseError) {
        Log.d(TAG,"modules request fail")
        onRequestListenerFailed.onFailed(Throwable())
    }
}