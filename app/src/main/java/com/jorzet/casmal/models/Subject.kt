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

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 16/08/19.
 */

data class Subject(
    @SerializedName("internalName")
    @Expose
    var internalName : SubjectType = SubjectType.NONE,
    @SerializedName("nameToDisplay")
    @Expose
    var nameToDisplay: String = "",
    // this is to identify course exmp. s1, s2, s3
    var subjectId: String = "",

    var subjectAverage : Double = 0.0
)