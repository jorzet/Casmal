package com.jorzet.casmal.request

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.jorzet.casmal.models.Subject
import org.json.JSONObject

class SubjectsRequest: AbstractRequestDatabase<String, List<Subject>>() {

    companion object {
        /**
         * Constants
         */
        private const val TAG: String = "SubjectsRequest"
        private const val SUBJECTS_REFERENCE: String = "subjects"
    }

    override fun getReference(): String? {
        return SUBJECTS_REFERENCE
    }

    override fun onGettingResponse(successResponse: DataSnapshot) {
        Log.d(TAG,"subjects request success")
        val post = successResponse.value
        if (post != null) {
            val subjectsMap = (post as HashMap<*, *>)
            val mSubjects = ArrayList<Subject>()
            for (key in subjectsMap.keys) {
                val subjectMap = subjectsMap[key] as HashMap<*, *>
                try {
                    val subject = Gson().fromJson(JSONObject(subjectMap).toString(), Subject::class.java)
                    // just save enabled subject
                    if (subject.enabled) {
                        mSubjects.add(subject)
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
            onRequestListenerSuccess.onSuccess(mSubjects)
        } else {
            onRequestListenerFailed.onFailed(Throwable())
        }
    }

    override fun onGettingError(errorResponse: DatabaseError) {
        Log.d(TAG,"subjects request fail")
        onRequestListenerFailed.onFailed(Throwable())
    }
}