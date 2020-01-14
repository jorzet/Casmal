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

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

class FirebaseStorageManager {
    companion object {
        /**
         * Manager static instance
         */
        private var instance: FirebaseStorage? = null

        /**
         * Creates a [FirebaseStorageManager] implementation instance
         *
         * @return A [FirebaseStorageManager] instance
         */
        fun getInstance(): FirebaseStorage {
            if (instance == null) {
                synchronized(FirebaseStorageManager::class.java) {
                    if (instance == null) {
                        instance = FirebaseStorage.getInstance()
                    }
                }
            }
            return instance!!
        }

        fun getImage(storageName: String): StorageReference {
            return getInstance().reference.child(storageName)
        }
    }
}