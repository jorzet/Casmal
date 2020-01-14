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

import com.google.android.gms.tasks.OnCompleteListener


/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 17/12/19.
 */

abstract class AbstractUpdateDatabase<A, B>: AbstractDatabase<A, B>() {


    /**
     * This is called when update is success
     */
    abstract fun onUpdateSuccess()

    /**
     * This method gives the fail response on param
     *
     * @param errorResponse error response object [Throwable]
     */
    abstract fun onUpdateError(errorResponse: Throwable)

    protected open fun getParams(): HashMap<String, Any>? {
        return null
    }

    open fun update() {
        // get firebase data base reference
        mFirebaseDatabase = getDatabaseInstance()

        mFirebaseDatabase.keepSynced(false)

        val params = getParams()
        if (params != null) {
            mFirebaseDatabase.updateChildren(params).addOnCompleteListener(mOnCompleteListener)
        }
    }

    private val mOnCompleteListener = OnCompleteListener<Void> { task ->
            if (task.isComplete) {
                onUpdateSuccess()
            } else {
                onUpdateError(task.exception!!)
            }
        }
}