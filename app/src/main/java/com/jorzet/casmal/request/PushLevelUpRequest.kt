package com.jorzet.casmal.request

import com.google.firebase.auth.FirebaseAuth

class PushLevelUpRequest: AbstractUpdateDatabase<String, Void>() {

    companion object {
        const val USERS_REFERENCE = "users"
        const val FLASHCARDS_REFERENCE = "flashcards"
    }

    override fun getReference(): String? {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            return USERS_REFERENCE  + "/" + user.uid + "/" + FLASHCARDS_REFERENCE
        }
        return null
    }

    override fun getParams(): HashMap<String, Any>? {
        val averageParams = HashMap<String, Any>()

        averageParams[TOTAL_QUESTIONS] = mAverage.totalQuestions
        averageParams[ANSWERED_QUESTIONS] = mAverage.answeredQuestions
        averageParams[CORRECT] = mAverage.correct
        averageParams[INCORRECT] = mAverage.incorrect

        return averageParams
    }

    override fun onUpdateSuccess() {

    }

    override fun onUpdateError(errorResponse: Throwable) {

    }
}