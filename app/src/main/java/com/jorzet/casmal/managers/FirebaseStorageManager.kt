package com.jorzet.casmal.managers

import com.google.firebase.storage.FirebaseStorage
import com.jorzet.casmal.utils.Utils

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
                        instance = FirebaseStorage.getInstance("gs://drcasmal-62132.appspot.com")
                    }
                }
            }
            return instance!!
        }

        fun getImage(storageName: String): String {
            var path = ""
            getInstance().reference.child(storageName).downloadUrl.addOnSuccessListener {
                path = it.toString()
            }

            Utils.print("Image: $path")
            return path
        }
    }
}