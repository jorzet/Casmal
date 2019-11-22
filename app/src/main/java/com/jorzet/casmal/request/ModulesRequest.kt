package com.jorzet.casmal.request

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.jorzet.casmal.models.Module
import org.json.JSONObject
import java.lang.Exception
import java.util.ArrayList

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
        Log.d("","")
        val post = successResponse.value
        if (post != null) {
            val modulesMap = (post as kotlin.collections.HashMap<*, *>)
            val mModules = ArrayList<Module>()
            for (key in modulesMap.keys) {
                val moduleMap = modulesMap.get(key) as kotlin.collections.HashMap<*, *>
                try {
                    val module = Gson().fromJson(JSONObject(moduleMap).toString(), Module::class.java)
                    mModules.add(module)
                } catch (e: Exception) {
                    e.printStackTrace()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
            onRequestListenerSucces.onSuccess(mModules)
        } else {
            onRequestLietenerFailed.onFailed(Throwable())
        }
    }

    override fun onGettingError(errorResponse: DatabaseError) {
        onRequestLietenerFailed.onFailed(Throwable())
    }
}