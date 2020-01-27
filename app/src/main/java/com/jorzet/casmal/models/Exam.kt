package com.jorzet.casmal.models


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

@Parcelize
data class Exam (
    @SerializedName("id")
    @Expose
    var examId: String = "",
    @SerializedName("name")
    @Expose
    var examName: String = "",
    @SerializedName("level")
    @Expose
    var level: String = "",
    @SerializedName("enabled")
    @Expose
    var enabled: Boolean = false,
    @SerializedName("difficulty")
    @Expose
    var difficulty: DifficultyType = DifficultyType.EASY,
    @SerializedName("purchase_type")
    @Expose
    var purchaseType: PurchaseType = PurchaseType.NONE,
    @SerializedName("questions")
    @Expose
    var questions: List<String> = arrayListOf()
): Parcelable