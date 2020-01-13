package com.jorzet.casmal.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 07/01/20.
 */

@Parcelize
data class Level(
    @SerializedName("flashcard")
    @Expose
    var flashcard: String,
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    @SerializedName("points")
    @Expose
    var points: Int = 0
): Parcelable