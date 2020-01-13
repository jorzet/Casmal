package com.jorzet.casmal.request

import com.jorzet.casmal.models.User

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

class PushUserRequest(private val uid: String): AbstractUpdateDatabase<User, Boolean>() {
    companion object {
        const val USERS_REFERENCE: String = "users"
    }

    override fun getReference(): String? {
        return USERS_REFERENCE
    }

    override fun getParams(): HashMap<String, Any>? {
        val user = User()

        val userMap = HashMap<String, Any>()
        val userParams = HashMap<String, Any>()

        userParams["deviceOS"] = user.deviceOS
        userParams["level"] = user.level
        userParams["points"] = user.points
        userParams["payment"] = user.payment
        userMap[uid] = userParams

        return userMap
    }

    override fun onUpdateSuccess() {
        onRequestListenerSuccess.onSuccess(true)
    }

    override fun onUpdateError(errorResponse: Throwable) {
        onRequestListenerFailed.onFailed(errorResponse)
    }
}