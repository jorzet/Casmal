package com.jorzet.casmal.request

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.gson.Gson
import com.jorzet.casmal.models.Flashcard
import org.json.JSONObject

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 24/12/19.
 */

class FlashcardRequest(): AbstractRequestDatabase<String, Any>() {

    companion object {
        const val TAG: String = "FlashcardRequest"
        const val FLASHCARD_REFERENCE: String = "flashcards"
    }

    private lateinit var mFlashcardId: String

    override fun getReference(): String? {
        if (::mFlashcardId.isInitialized) {
            return "$FLASHCARD_REFERENCE/$mFlashcardId"
        } else {
            return FLASHCARD_REFERENCE
        }
    }

    /**
     * This constructor is to call just one flashcard
     */
    constructor(flashcardId: String): this() {
        mFlashcardId = flashcardId
    }

    override fun onGettingResponse(successResponse: DataSnapshot) {
        Log.d(TAG,"flashcard request success")

        if (::mFlashcardId.isInitialized) {
            val flashcard: Flashcard? = successResponse.getValue(Flashcard::class.java)
            if (flashcard != null) {
                onRequestListenerSuccess.onSuccess(flashcard)
            } else {
                onRequestListenerFailed.onFailed(Throwable())
            }
        } else {
            val post = successResponse.value
            if (post != null) {
                val flashcardsMap = (post as HashMap<*, *>)
                val mFlashcards = ArrayList<Flashcard>()
                for (key in flashcardsMap.keys) {
                    val flashcardMap = flashcardsMap[key] as HashMap<*, *>
                    try {
                        val flashcard = Gson().fromJson(JSONObject(flashcardMap).toString(), Flashcard::class.java)
                        // just save enabled subject
                        mFlashcards.add(flashcard)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
                onRequestListenerSuccess.onSuccess(mFlashcards)
            } else {
                onRequestListenerFailed.onFailed(Throwable())
            }
        }
    }

    override fun onGettingError(errorResponse: DatabaseError) {
        Log.d(TAG,"flashcard request fail")
        onRequestListenerFailed.onFailed(Throwable())
    }
}