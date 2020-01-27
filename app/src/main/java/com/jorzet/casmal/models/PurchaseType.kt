package com.jorzet.casmal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

enum class PurchaseType(val type: String) {
    @SerializedName("free")
    @Expose
    FREE("free"),
    @SerializedName("premium")
    @Expose
    PREMIUM("premium"),
    NONE("")
}