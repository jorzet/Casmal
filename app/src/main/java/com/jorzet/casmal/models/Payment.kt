package com.jorzet.casmal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Payment (
    var confirming: Boolean = false,
    var isPremium: Boolean = false,
    var subscription: String,
    var timeStamp: Int
) : Parcelable {
    constructor() : this(false, false, "", 0)
}