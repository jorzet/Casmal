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

package com.jorzet.casmal.utils

import android.util.Log

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

class Utils {
    companion object {
        const val PROVIDER_FACEBOOK = "Facebook"
        const val PROVIDER_GOOGLE = "Google"

        fun print(TAG: String, text: String) {
            if(text.isEmpty()) {
                return
            }

            Log.d(TAG, text)
        }

        fun print(text: String) {
            if(text.isEmpty()) {
                return
            }

            Log.d("Bani", text)
        }
    }
}