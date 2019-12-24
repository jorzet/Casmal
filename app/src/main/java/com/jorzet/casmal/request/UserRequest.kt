package com.jorzet.casmal.request

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.jorzet.casmal.models.User
import com.jorzet.casmal.utils.Utils

class UserRequest(private val uid: String): AbstractRequestDatabase<String, User?>() {
    override fun getReference(): String? {
        return null
    }

    override fun onGettingResponse(successResponse: DataSnapshot) {
        Utils.print("Users request success with $uid received")

        val dataSnapshots: Iterator<DataSnapshot> = successResponse.children.iterator()
        val users: MutableList<User> = ArrayList()

        while (dataSnapshots.hasNext()) {
            val childSnapshot: DataSnapshot = dataSnapshots.next()
            Utils.print(successResponse.toString())
            val user: User? = childSnapshot.getValue(User::class.java)
            users.add(user!!)

            Utils.print("Adding: " + user.uid)
        }

        val temp: MutableList<User> = ArrayList()

        if(users.isEmpty()) {
            Utils.print("¡Usuario nuevo!")
            onRequestListenerSuccess.onSuccess(null)
            return
        } else {
            Utils.print("Users size: " + users.size)
        }

        Utils.print("User found: $uid")

        if (users[0].uid == uid) {
            temp.add(users[0])
            //Here you can find your searchable user
            Utils.print(temp[0].uid)
            onRequestListenerSuccess.onSuccess(users[0])
        }
    }

    override fun onGettingError(errorResponse: DatabaseError) {
        Utils.print("onGettingError users: $errorResponse")
    }
}