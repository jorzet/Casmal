package com.jorzet.casmal.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 17/12/19.
 */

@Parcelize
data class User (
    @SerializedName("uid")
    @Expose
    var uid: String = "",

    @SerializedName("deviceOS")
    @Expose
    var deviceOS: String = "Android",

    @SerializedName("level")
    @Expose
    var level: Int = 0,

    @SerializedName("payment")
    @Expose
    var payment: Payment
) : Parcelable {
    constructor() : this("", "Android", 0, Payment())
}