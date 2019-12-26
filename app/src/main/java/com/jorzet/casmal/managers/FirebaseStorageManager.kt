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