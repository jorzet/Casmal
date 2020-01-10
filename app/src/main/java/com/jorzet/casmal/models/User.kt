package com.jorzet.casmal.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jorzet.casmal.utils.Utils
import kotlinx.android.parcel.Parcelize
import okhttp3.internal.Util

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 17/12/19.
 */

@Parcelize
data class User (
    @SerializedName("deviceOS")
    @Expose
    var deviceOS: String = "Android",

    @SerializedName("level")
    @Expose
    var level: Int = 0,

    @SerializedName("payment")
    @Expose
    var payment: Payment = Payment(),

    @SerializedName("flashcards")
    @Expose
    var flashCards: List<String> = emptyList()
) : Parcelable {
    constructor() : this("Android", 0, Payment())

    override fun toString(): String {
        Utils.print("deviceOS: $deviceOS")
        Utils.print("level: $level")
        Utils.print("payment:")
        Utils.print("payment.confirming ${payment.confirming}")
        Utils.print("payment.isPremium ${payment.isPremium}")
        Utils.print("payment.subscription ${payment.subscription}")
        Utils.print("payment.timeStamp ${payment.timeStamp}")
        Utils.print("flashcards $flashCards")

        return "User"
    }
}