/*
 * Copyright [2020] [Jorge Zepeda Tinoco]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jorzet.casmal.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 087/08/19.
 */

@Parcelize
data class Question (
    @SerializedName("answer")
    @Expose
    var answer : String = "",
    @SerializedName("level")
    @Expose
    var level : String = "",
    @SerializedName("subject_type")
    @Expose
    var subject : SubjectType = SubjectType.NONE,
    @SerializedName("question_type")
    @Expose
    var questionType : QuestionType = QuestionType.NONE,
    @SerializedName("points")
    @Expose
    var points : String = "",
    @SerializedName("text")
    @Expose
    var text : String = "",
    @SerializedName("opt1")
    @Expose
    var opt1 : String? = "",
    @SerializedName("opt2")
    @Expose
    var opt2 : String? = "",
    @SerializedName("opt3")
    @Expose
    var opt3 : String? = "",
    @SerializedName("opt4")
    @Expose
    var opt4 : String? = "",
    @SerializedName("id")
    @Expose
    var questionId : String = "",
    @SerializedName("enabled")
    @Expose
    var enabled : Boolean = false,

    var chosenOption : String = "",
    var wasOK : Boolean = false,
    var answered : Boolean = false, // important this is just used in ShowQuestionsActivity
    var moduleId: String // just set it in case exam is open
) : Parcelable {
    constructor(): this("","", SubjectType.NONE, QuestionType.NONE, "",
        "", "", "", "", "", "", false,
        "", false, false, "")
}