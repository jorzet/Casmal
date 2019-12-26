package com.jorzet.casmal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Payment (
    var confirming: Boolean = false,
    var isPremium: Boolean = false,
    var subscription: String,
    var timeStamp: Long
) : Parcelable {
    constructor() : this(false, false, "", Date().time)
}