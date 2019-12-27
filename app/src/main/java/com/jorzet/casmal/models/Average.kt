package com.jorzet.casmal.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 27/12/19.
 */

data class Average(
    @SerializedName("total_questions")
    @Expose
    var totalQuestions: Int = 0,
    @SerializedName("answered_questions")
    @Expose
    var answeredQuestions: Int = 0,
    @SerializedName("correct")
    @Expose
    var correct: Int = 0,
    @SerializedName("incorrect")
    @Expose
    var incorrect: Int = 0,
    var subjectType: SubjectType = SubjectType.NONE,
    var moduleId: String = ""
)
