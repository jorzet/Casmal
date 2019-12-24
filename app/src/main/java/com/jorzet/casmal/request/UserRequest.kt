package com.jorzet.casmal.request

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.jorzet.casmal.models.User
import com.jorzet.casmal.utils.Utils

class UserRequest(private val uid: String): AbstractRequestDatabase<String, User?>() {
    companion object {
        /**
         * Constants
         */
        private const val TAG: String = "UserRequest"
        private const val USERS_REFERENCE: String = "users"
    }

    override fun getReference(): String? {
        return "$USERS_REFERENCE/$uid"
    }

    override fun onGettingResponse(successResponse: DataSnapshot) {
        Utils.print("$TAG Users request success with $uid received")

        val user: User? = successResponse.getValue(User::class.java)

        if (user != null) {
            Utils.print("¡User found!")
            Utils.print(user.toString())
            onRequestListenerSuccess.onSuccess(user)
            mFirebaseDatabase.removeEventListener(mValueEventListener)
        } else {
            Utils.print("¡User not found!")
            onRequestListenerSuccess.onSuccess(null)
        }
    }

    override fun onGettingError(errorResponse: DatabaseError) {
        Utils.print("$TAG onGettingError users: $errorResponse")
    }
}