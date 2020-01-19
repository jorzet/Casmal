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

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 17/12/19.
 */

abstract class AbstractDatabase<A, B> {
    /*
     * Database object
     */
    protected lateinit var mFirebaseDatabase: DatabaseReference

    /**
     * Listeners
     */
    protected lateinit var onRequestListenerSuccess: OnRequestListenerSuccess<B>
    protected lateinit var onRequestListenerFailed: OnRequestListenerFailed

    /** Describes success interface listener*/
    interface OnRequestListenerSuccess<B> {
        /**
         * Success method, gives B as output response
         */
        fun onSuccess(result: B)
    }

    /** Describes fail interface listener */
    interface OnRequestListenerFailed {
        /**
         * Fail method, gives [Throwable] as output error response
         */
        fun onFailed(throwable: Throwable)
    }

    /**
     * Set success request listener
     *
     * @param onRequestSuccess [OnRequestListenerSuccess] implementation
     */
    fun setOnRequestSuccess(onRequestSuccess: OnRequestListenerSuccess<B>) {
        this.onRequestListenerSuccess = onRequestSuccess
    }

    /**
     * Set fail request listener
     *
     * @param onRequestFailed [OnRequestListenerFailed] implementation
     */
    fun setOnRequestFailed(onRequestFailed: OnRequestListenerFailed) {
        this.onRequestListenerFailed = onRequestFailed
    }

    /**
     * This method gives database reference
     *
     * @return reference in [String]
     */
    abstract fun getReference(): String?

    /**
     * Create a Firebase Database INSTANCE according reference
     *
     * @return An INSTANCE of [DatabaseReference]
     */
    protected open fun getDatabaseInstance(): DatabaseReference {
        val reference = getReference()

        if (reference != null) {
            return FirebaseDatabase
                .getInstance()
                .getReference(reference)
        }

        return FirebaseDatabase.getInstance().reference
    }
}