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