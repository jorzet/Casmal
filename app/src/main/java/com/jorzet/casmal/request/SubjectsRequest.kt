package com.jorzet.casmal.request

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.jorzet.casmal.models.Subject
import org.json.JSONObject
import java.lang.Exception

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
            val subjectsMap = (post as kotlin.collections.HashMap<*, *>)
            val mSubjects = ArrayList<Subject>()
            for (key in subjectsMap.keys) {
                val subjectMap = subjectsMap.get(key) as kotlin.collections.HashMap<*, *>
                try {
                    val subject = Gson().fromJson(JSONObject(subjectMap).toString(), Subject::class.java)
                    mSubjects.add(subject)
                } catch (e: Exception) {
                    e.printStackTrace()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
            onRequestListenerSucces.onSuccess(mSubjects)
        } else {
            onRequestLietenerFailed.onFailed(Throwable())
        }
    }

    override fun onGettingError(errorResponse: DatabaseError) {
        Log.d(TAG,"subjects request fail")
        onRequestLietenerFailed.onFailed(Throwable())
    }

}