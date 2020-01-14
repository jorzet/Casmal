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

package com.jorzet.casmal.request

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.jorzet.casmal.models.Module
import org.json.JSONObject
import java.util.*

class ModulesRequest: AbstractRequestDatabase<String, List<Module>>() {

    companion object {
        /**
         * Constants
         */
        private const val TAG: String = "ModulesRequest"
        private const val MODULES_REFERENCE: String = "modules"
    }

    override fun getReference(): String? {
        return MODULES_REFERENCE
    }

    override fun onGettingResponse(successResponse: DataSnapshot) {
        Log.d(TAG,"modules request success")
        val post = successResponse.value
        if (post != null) {
            val modulesMap = (post as HashMap<*, *>)
            val mModules = ArrayList<Module>()
            for (key in modulesMap.keys) {
                val moduleMap = modulesMap[key] as HashMap<*, *>
                try {
                    val module = Gson().fromJson(JSONObject(moduleMap).toString(), Module::class.java)

                    // just save enabled module
                    if (module.enabled) {
                        mModules.add(module)
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

            onRequestListenerSuccess.onSuccess(mModules)
        } else {
            onRequestListenerFailed.onFailed(Throwable())
        }
    }

    override fun onGettingError(errorResponse: DatabaseError) {
        Log.d(TAG,"modules request fail")
        onRequestListenerFailed.onFailed(Throwable())
    }
}