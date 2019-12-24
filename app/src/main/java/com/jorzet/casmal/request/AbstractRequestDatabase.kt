package com.jorzet.casmal.request

/*
 * Copyright [2019] [Jorge Zepeda Tinoco]
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

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 07/08/19.
 *
 *
 * [AbstractRequestDatabase] Describes all methods that are going to be used on
 *                           Firebase request passing the following params:
 *
 * A: input
 * B: output
 */
abstract class AbstractRequestDatabase<A, B>: AbstractDatabase<A, B>() {

    /**
     * Attributes
     */
    private var mResponse: B? = null
    private var mParams: A? = null

    /**
     * This method gives the success response on param
     *
     * @param successResponse success response object [DataSnapshot]
     */
    abstract fun onGettingResponse(successResponse: DataSnapshot)

    /**
     * This method gives the fail response on param
     *
     * @param errorResponse error response object [DatabaseError]
     */
    abstract fun onGettingError(errorResponse: DatabaseError)

    /**
     * Indicate if data is sync and storage
     *
     * @return
     */
    protected open fun keepSync(): Boolean {
        return true
    }

    protected open fun getQuery(): Query? {
        return null
    }

    /**
     * This method gets data, with the given reference and return it on success and error methods
     */
    open fun request() {
        // get firebase data base reference
        mFirebaseDatabase = getDatabaseInstance()

        // set keep sync
        mFirebaseDatabase.keepSynced(keepSync())

        val query = getQuery()

        // Attach a listener to read the data at our posts reference
        if (query != null) {
            query.addValueEventListener(mValueEventListener)
        } else {
            mFirebaseDatabase.addValueEventListener(mValueEventListener)
        }
    }

    /**
     * This method gets data, with the given reference and search label then return it on success and error methods
     */
    open fun request(pathString: String, path: String, value: String) {
        // get firebase data base reference
        mFirebaseDatabase = getDatabaseInstance()

        // set keep sync
        mFirebaseDatabase.keepSynced(keepSync())

        val query = getQuery()

        // Attach a listener to read the data at our posts reference
        if (query != null) {
            query.addValueEventListener(mValueEventListener)
        } else {
            mFirebaseDatabase.child(pathString).orderByChild(path).equalTo(value).addValueEventListener(mValueEventListener)
        }
    }

    private val mValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            onGettingResponse(dataSnapshot)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            onGettingError(databaseError)
        }
    }
}