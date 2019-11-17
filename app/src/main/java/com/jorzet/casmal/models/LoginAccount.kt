package com.jorzet.casmal.models

import androidx.annotation.IntDef

data class LoginAccount(var userName: String, var userId: String, var email: String, @LoginMethod var loginMethod: Int) {
    companion object {
        @IntDef(FACEBOOK, GOOGLE, EMAIL)
        @Retention(AnnotationRetention.SOURCE)
        annotation class LoginMethod

        const val FACEBOOK = 0
        const val GOOGLE = 1
        const val EMAIL = 2
    }
}