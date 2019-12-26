package com.jorzet.casmal.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

@Parcelize
data class Payment (
    @SerializedName("confirming")
    @Expose
    var confirming: Boolean = false,

    @SerializedName("isPremium")
    @Expose
    var isPremium: Boolean = false,

    @SerializedName("subscription")
    @Expose
    var subscription: String,

    @SerializedName("timeStamp")
    @Expose
    var timeStamp: Long
) : Parcelable {
    constructor() : this(false, false, "", Date().time)
}