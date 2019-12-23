package com.jorzet.casmal.request

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.jorzet.casmal.models.User
import com.jorzet.casmal.utils.Constants
import com.jorzet.casmal.utils.Utils
import org.json.JSONObject

class UserRequest(uid: String): AbstractRequestDatabase<String, User>() {
    override fun getReference(): String? {
        return Constants.tableUsers
    }

    override fun onGettingResponse(successResponse: DataSnapshot) {
        Utils.print("Users request success")
        val post = successResponse.value
        if (post != null) {
            val userMap = (post as HashMap<*, *>)
            val userJson = JSONObject(userMap).toString()

            try {
                val user = Gson().fromJson(userJson, User::class.java)
                onRequestListenerSuccess.onSuccess(user)
            } catch (ex: Exception) {
                Utils.print("Exception users: $ex")
            }
        } else {
            onRequestListenerFailed.onFailed(Throwable("Post user response is null"))
        }
    }

    override fun onGettingError(errorResponse: DatabaseError) {
        Utils.print("onGettingError users: $errorResponse")
    }
}