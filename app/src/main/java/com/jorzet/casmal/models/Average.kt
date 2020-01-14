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
