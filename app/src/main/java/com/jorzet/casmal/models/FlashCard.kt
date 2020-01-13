package com.jorzet.casmal.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 24/12/19.
 */

@Parcelize
data class FlashCard(
    @SerializedName("id")
    @Expose
    var id: String,
    @SerializedName("storageName")
    @Expose
    var storageName: String
): Parcelable {
    constructor(): this("", "")
}