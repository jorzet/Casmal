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
import com.jorzet.casmal.models.FlashCard
import org.json.JSONObject

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 24/12/19.
 */

class FlashCardsRequest(): AbstractRequestDatabase<String, MutableList<FlashCard>>() {
    companion object {
        const val TAG: String = "FlashCardsRequest"
        const val FLASHCARD_REFERENCE: String = "flashcards"
    }

    private lateinit var mFlashCardId: String

    override fun getReference(): String? {
        return if (::mFlashCardId.isInitialized) {
            "$FLASHCARD_REFERENCE/$mFlashCardId"
        } else {
            FLASHCARD_REFERENCE
        }
    }

    /**
     * This constructor is to call just one flashcard
     */
    constructor(flashCardId: String): this() {
        mFlashCardId = flashCardId
    }

    override fun onGettingResponse(successResponse: DataSnapshot) {
        Log.d(TAG,"flashCardRequest success")

        if (::mFlashCardId.isInitialized) {
            val flashCard: FlashCard? = successResponse.getValue(FlashCard::class.java)
            if (flashCard != null) {
                val list: MutableList<FlashCard> = ArrayList()
                list.add(flashCard)
                onRequestListenerSuccess.onSuccess(list)
            } else {
                onRequestListenerFailed.onFailed(Throwable())
            }
        } else {
            val post = successResponse.value
            if (post != null) {
                val flashCardsMap = (post as HashMap<*, *>)
                val mFlashCards: MutableList<FlashCard> = ArrayList()
                for (key in flashCardsMap.keys) {
                    val flashCardMap = flashCardsMap[key] as HashMap<*, *>
                    try {
                        val flashCard = Gson().fromJson(JSONObject(flashCardMap).toString(), FlashCard::class.java)
                        // just save enabled subject
                        mFlashCards.add(flashCard)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
                onRequestListenerSuccess.onSuccess(mFlashCards)
            } else {
                onRequestListenerFailed.onFailed(Throwable())
            }
        }
    }

    override fun onGettingError(errorResponse: DatabaseError) {
        Log.d(TAG,"flashCardRequest fail")
        onRequestListenerFailed.onFailed(Throwable())
    }
}