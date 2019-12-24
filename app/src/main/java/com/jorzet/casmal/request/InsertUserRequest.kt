package com.jorzet.casmal.request

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jorzet.casmal.models.User
import com.jorzet.casmal.utils.Constants


class InsertUserRequest(private val user: User): AbstractRequestDatabase<String, User>() {
    companion object {
        const val reference: String = "users"
    }

    override fun getReference(): String? {
        return reference
    }

    override fun onGettingResponse(successResponse: DataSnapshot) {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
        reference.child(Constants.tableUsers).setValue(user)
    }

    override fun onGettingError(errorResponse: DatabaseError) {

    }
}