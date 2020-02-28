package com.jorzet.casmal.request

import com.jorzet.casmal.models.User

class PushPayment (private var uid: String): AbstractUpdateDatabase<User, Boolean>() {
    companion object {
        const val USERS_REFERENCE: String = "users"
        const val PAYMENT_REFERENCE: String = "payment"
    }

    private var user: User? = null

    constructor(uid: String, user: User): this(uid) {
        this.uid = uid
        this.user = user
    }

    override fun getReference(): String? {
        return "$USERS_REFERENCE/$uid/$PAYMENT_REFERENCE"
    }

    override fun getParams(): HashMap<String, Any>? {

        if (user == null ) {
            user = User()
        }

        val paymentParams = HashMap<String, Any>()

        paymentParams["confirming"] = user!!.payment.confirming
        paymentParams["premium"] = user!!.payment.isPremium
        paymentParams["subscription"] = user!!.payment.subscription
        paymentParams["timeStamp"] = user!!.payment.timeStamp

        return paymentParams
    }

    override fun onUpdateSuccess() {
        onRequestListenerSuccess.onSuccess(true)
    }

    override fun onUpdateError(errorResponse: Throwable) {
        onRequestListenerFailed.onFailed(errorResponse)
    }
}