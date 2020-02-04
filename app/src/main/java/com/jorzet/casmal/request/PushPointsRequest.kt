/*
 * Copyright [2020] [Bani Azarael Mejia Flores]
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

import com.google.firebase.auth.FirebaseAuth
import com.jorzet.casmal.models.User

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 31/01/20.
 */

class PushPointsRequest(private val user: User): AbstractUpdateDatabase<String, Boolean>() {
    companion object {
        const val USERS_REFERENCE = "users"
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

        userLevelParams[POINTS_PARAM] = user.points

        return userLevelParams
    }

    override fun onUpdateSuccess() {
        onRequestListenerSuccess.onSuccess(true)
    }

    override fun onUpdateError(errorResponse: Throwable) {
        onRequestListenerFailed.onFailed(Throwable())
    }
}