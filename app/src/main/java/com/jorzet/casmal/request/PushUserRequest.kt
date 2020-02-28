/*
 * Copyright [2020] [Jorge Zepeda Tinoco]
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

import com.jorzet.casmal.models.User

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

class PushUserRequest(private var uid: String): AbstractUpdateDatabase<User, Boolean>() {
    companion object {
        const val USERS_REFERENCE: String = "users"
    }

    private var user: User? = null

    constructor(uid: String, user: User): this(uid) {
        this.uid = uid
        this.user = user
    }

    override fun getReference(): String? {
        return USERS_REFERENCE
    }

    override fun getParams(): HashMap<String, Any>? {

        if (user == null) {
            user = User()
        }

        val userMap = HashMap<String, Any>()
        val userParams = HashMap<String, Any>()

        userParams["specialUser"] = user!!.specialUser
        userParams["deviceOS"] = user!!.deviceOS
        userParams["level"] = user!!.level
        userParams["points"] = user!!.points
        userParams["payment"] = user!!.payment
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