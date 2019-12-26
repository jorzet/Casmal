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

class FlashCardRequest(): AbstractRequestDatabase<String, List<FlashCard>>() {

    companion object {
        const val TAG: String = "FlashCardRequest"
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
                val list: ArrayList<FlashCard> = ArrayList()
                list.add(flashCard)
                onRequestListenerSuccess.onSuccess(list)
            } else {
                onRequestListenerFailed.onFailed(Throwable())
            }
        } else {
            val post = successResponse.value
            if (post != null) {
                val flashCardsMap = (post as HashMap<*, *>)
                val mFlashCards = ArrayList<FlashCard>()
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