package com.jorzet.casmal.managers

import com.google.firebase.storage.FirebaseStorage

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
                        instance = FirebaseStorage.getInstance("gs://my-custom-bucket")
                    }
                }
            }
            return instance!!
        }

        fun getImage(storageName: String): String {
            return getInstance().reference.child(storageName).path
        }
    }
}