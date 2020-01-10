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

class LevelsRequest(): AbstractRequestDatabase<String, List<Level>>() {

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
            val mLevels = ArrayList<Level>()
            for (level in levelsList) {
                try {
                    val levelMap = level as HashMap<*, *>
                    val level_ = Gson().fromJson(JSONObject(levelMap).toString(), Level::class.java)
                    mLevels.add(level_)
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