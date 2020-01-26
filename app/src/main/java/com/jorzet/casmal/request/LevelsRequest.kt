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

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.jorzet.casmal.models.Level
import org.json.JSONObject

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 07/01/20.
 */

class LevelsRequest: AbstractRequestDatabase<String, MutableList<Level>>() {

    companion object {
        const val LEVELS_REFERENCE: String = "levels"
    }

    override fun getReference(): String? {
        return LEVELS_REFERENCE
    }

    override fun onGettingResponse(successResponse: DataSnapshot) {
        val post = successResponse.value
        if (post != null) {
            val levelsList = (post as List<*>)
            val mLevels: MutableList<Level> = ArrayList()
            for (level in levelsList) {
                try {
                    val levelMap = level as HashMap<*, *>
                    val levelObject = Gson().fromJson(JSONObject(levelMap).toString(), Level::class.java)
                    mLevels.add(levelObject)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
            onRequestListenerSuccess.onSuccess(mLevels)
        }
    }

    override fun onGettingError(errorResponse: DatabaseError) {
        onRequestListenerFailed.onFailed(Throwable())
    }
}