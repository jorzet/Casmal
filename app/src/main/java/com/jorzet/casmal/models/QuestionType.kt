package com.jorzet.casmal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

enum class QuestionType(name: String) {
    @SerializedName("true_false")
    @Expose
    TRUE_FALSE("true_false"),
    @SerializedName("multiple")
    @Expose
    MULTIPLE("multiple"),
    NONE("")
}