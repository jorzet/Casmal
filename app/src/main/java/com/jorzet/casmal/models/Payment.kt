package com.jorzet.casmal.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
    var subscription: String = "",

    @SerializedName("timestamp")
    @Expose
    var timestamp: Int = 0
) : Parcelable