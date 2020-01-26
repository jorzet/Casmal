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
import com.jorzet.casmal.utils.Utils
import kotlinx.android.parcel.Parcelize

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 24/12/19.
 */

@Parcelize
data class FlashCard(
    @SerializedName("id")
    @Expose
    var id: String,
    @SerializedName("storageName")
    @Expose
    var storageName: String
): Parcelable {
    constructor(): this("", "")

    override fun toString(): String {
        Utils.print("id: $id")
        Utils.print("storageName: $storageName")

        return "FlashCard content"
    }
}