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

package com.jorzet.casmal

import android.util.Log
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.jorzet.casmal.managers.ImageManager
import com.google.firebase.database.FirebaseDatabase

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 15/08/19.
 */

class CasmalApplication: MultiDexApplication() {

    private val TAG: String = "CasmalApplication"

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }

        ImageManager.getInstance().initialize(this)

        try {
            FirebaseDatabase
                .getInstance()
                .setPersistenceEnabled(true)
        } catch (e: Exception) {
            Log.d(TAG, "cannot set persistence database")
        }

    }
}