package com.jorzet.casmal.request

import com.google.firebase.auth.FirebaseAuth
import com.jorzet.casmal.repositories.UserRepository

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 10/01/20.
 */

class PushLevelUpRequest: AbstractUpdateDatabase<String, Boolean>() {

    companion object {
        const val USERS_REFERENCE = "users"
        const val FLASHCARDS_PARAM = "flashcards"
        const val LEVEL_PARAM = "level"
        const val POINTS_PARAM = "points"
    }

    override fun getReference(): String? {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            return USERS_REFERENCE  + "/" + user.uid + "/"
        }
        return null
    }

    override fun getParams(): HashMap<String, Any>? {
        val userLevelParams = HashMap<String, Any>()

        val user = UserRepository.instance.getUser().value
        if (user != null) {
            userLevelParams[LEVEL_PARAM] = user.level
            userLevelParams[POINTS_PARAM] = user.points
            userLevelParams[FLASHCARDS_PARAM] = user.flashCards
        }

        return userLevelParams
    }

    override fun onUpdateSuccess() {
        onRequestListenerSuccess.onSuccess(true)
    }

    override fun onUpdateError(errorResponse: Throwable) {
        onRequestListenerFailed.onFailed(Throwable())
    }
}