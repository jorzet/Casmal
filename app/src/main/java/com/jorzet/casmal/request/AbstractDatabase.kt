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
    protected lateinit var onRequestListenerSucces: OnRequestListenerSuccess<B>
    protected lateinit var onRequestLietenerFailed: OnRequestListenerFailed

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
        this.onRequestListenerSucces = onRequestSuccess
    }

    /**
     * Set fail request listener
     *
     * @param onRequestFailed [OnRequestListenerFailed] implementation
     */
    fun setOnRequestFailed(onRequestFailed: OnRequestListenerFailed) {
        this.onRequestLietenerFailed = onRequestFailed
    }

    /**
     * This method gives database reference
     *
     * @return reference in [String]
     */
    abstract fun getReference(): String?

    /**
     * Create a Firebase Database instance according reference
     *
     * @return An isntance of [DatabaseReference]
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