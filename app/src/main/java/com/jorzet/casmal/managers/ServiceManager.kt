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

package com.jorzet.casmal.managers

import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.models.Level
import com.jorzet.casmal.models.User
import com.jorzet.casmal.utils.Utils

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 10/01/20.
 */

class ServiceManager {

    /**
     * Models
     */
    var user: User? = null
    var userFlashCards: List<FlashCard> = emptyList()
    var flashCards: List<FlashCard> = emptyList()
    var levels: List<Level> = emptyList()

    companion object {
        @Volatile
        private var instance: ServiceManager? = null

        fun getInstance(): ServiceManager {
            if (instance != null) {
                return instance as ServiceManager
            }

            synchronized(this) {
                val instance = ServiceManager()
                this.instance = instance

                return instance
            }
        }

        init {
            Utils.print("Instance", "Instance ImageManager = " + instance.hashCode())
        }
    }

    /**
     * Destroy [ServiceManager] instance
     */
    /*fun destroy() {
        instance = null
    }*/
}