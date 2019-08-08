package com.jorzet.casmal.request

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.jorzet.casmal.models.Question

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 17/07/19.
 */

/**
 * Constants
 */
private const val TAG: String = "QuestionsTask"
private const val QUESTIONS_REFERENCE: String = "questions"

class QuestionsRequest(questionId: String): AbstractRequestDatabase<String, Question>() {

    /**
     * Attributes
     */
    private val mQuestionId: String = questionId

    override fun getReference(): String? {
        return QUESTIONS_REFERENCE
    }

    override fun onGettingResponse(successResponse: DataSnapshot) {

    }

    override fun onGettingError(errorResponse: DatabaseError) {

    }

}