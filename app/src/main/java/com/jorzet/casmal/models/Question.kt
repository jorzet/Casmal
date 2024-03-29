/*
 * Copyright [2019] [Jorge Zepeda Tinoco]
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

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 087/08/19.
 */

data class Question (
    @SerializedName("answer")
    @Expose
    var answer : String = "",
    @SerializedName("optionsData")
    @Expose
    var optionsData : List<String> = arrayListOf(),
    @SerializedName("optionsTypes")
    @Expose
    var optionsTypes : List<String> = arrayListOf(),
    @SerializedName("questionData")
    @Expose
    var questionData : List<String> = arrayListOf(),
    @SerializedName("questionTypes")
    @Expose
    var questionTypes : List<String> = arrayListOf(),
    @SerializedName("subject")
    @Expose
    var subject : SubjectType = SubjectType.NONE,
    @SerializedName("subtopic")
    @Expose
    var subtopic : String = "",
    @SerializedName("topic")
    @Expose
    var topic : String = "",
    @SerializedName("year")
    @Expose
    var year : String = "",

    var questionId : String = "",
    var chosenOption : String = "",
    var wasOK : Boolean = false,
    var answered : Boolean = false // important this is just used in ShowQuestionsActivity
) : Serializable