package com.jorzet.casmal.utils

import android.util.Log

class Utils {
    companion object {
        fun printDebug(TAG: String, text: String) {
            if(text.isEmpty()) {
                return
            }

            Log.d(TAG, text)
        }
    }
}