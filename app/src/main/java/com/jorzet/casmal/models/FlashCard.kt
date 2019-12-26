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
    @SerializedName("storage_name")
    @Expose
    var storageName: String,
    @SerializedName("level")
    @Expose
    var level: Int
): Parcelable {
    constructor(): this("", "", 0)
}