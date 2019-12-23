package com.jorzet.casmal.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Payment (
    var confirming: Boolean,
    var isPremium: Boolean,
    var subscription: String,
    var timeStamp: Int
) : Parcelable {
    constructor() : this(false, false, "", 0)
}