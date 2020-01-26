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
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 17/12/19.
 */

@Parcelize
data class User (
    @SerializedName("deviceOS")
    @Expose
    var deviceOS: String = "Android",

    @SerializedName("level")
    @Expose
    var level: Int = 0,

    @SerializedName("points")
    @Expose
    var points: Int = 0,

    @SerializedName("payment")
    @Expose
    var payment: Payment = Payment(),

    @SerializedName("flashcards")
    @Expose
    var flashCards: ArrayList<String> = arrayListOf()

) : Parcelable {
    constructor() : this("Android", 0, 0, Payment())

    override fun toString(): String {
        Utils.print("deviceOS: $deviceOS")
        Utils.print("level: $level")
        Utils.print("payment:")
        Utils.print("payment.confirming ${payment.confirming}")
        Utils.print("payment.isPremium ${payment.isPremium}")
        Utils.print("payment.subscription ${payment.subscription}")
        Utils.print("payment.timeStamp ${payment.timeStamp}")
        Utils.print("flashcards $flashCards")

        return "User content"
    }
}