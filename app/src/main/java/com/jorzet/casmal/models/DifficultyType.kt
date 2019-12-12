package com.jorzet.casmal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

enum class DifficultyType(name: String) {
    @SerializedName("easy")
    @Expose
    EASY("easy"),
    @SerializedName("medium")
    @Expose
    MEDIUM("medium"),
    @SerializedName("hard")
    @Expose
    HARD("hard")
}