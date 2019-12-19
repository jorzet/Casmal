package com.jorzet.casmal.request

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.jorzet.casmal.models.Question

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 17/12/19.
 */

class PushQuestionRequest(isExam: Boolean, question: Question): AbstractUpdateDatabase<Question, Boolean>() {

    /**
     * Constants
     */
    companion object {
        private const val TAG: String = "QuestionsTask"
        private const val USERS_REFERENCE: String = "users"
        private const val CHOSEN_OPTION_PARAM: String = "chosenOption"
        private const val QUESTION_TYPE_PARAM: String = "question_type"
        private const val IS_CORRECT_PARAM: String = "isCorrect"
        private const val SUBJECT_PARAM: String = "subject"
        private const val ANSWERED_EXAMS_PARAM: String = "answeredExams"
        private const val ANSWERED_QUESTIONS_PARAM: String = "answeredQuestions"
    }

    /**
     * Attributes
     */
    private val mIsExam: Boolean = isExam
    private val mQuestion: Question = question

    @SuppressLint("DefaultLocale")
    override fun getReference(): String? {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            return USERS_REFERENCE + "/" + user.uid + "/" +
                    if (mIsExam) {
                        ANSWERED_EXAMS_PARAM
                    } else {
                        ANSWERED_QUESTIONS_PARAM + "/"  + mQuestion.subject.name.toLowerCase()
                    }
        }
        return null
    }

    @SuppressLint("DefaultLocale")
    override fun getParams(): HashMap<String, Any>? {
        val questionMap = HashMap<String, Any>()
        val questionParams = HashMap<String, Any>()

        questionParams[CHOSEN_OPTION_PARAM] = mQuestion.chosenOption
        questionParams[QUESTION_TYPE_PARAM] = mQuestion.questionType.name.toLowerCase()
        questionParams[IS_CORRECT_PARAM] = mQuestion.wasOK
        questionParams[SUBJECT_PARAM] = mQuestion.subject.name.toLowerCase()
        questionMap[mQuestion.questionId] = questionParams

        return questionMap
    }

    override fun onUpdateSuccess() {
        Log.d(TAG,"question update success")
        onRequestListenerSucces.onSuccess(true)
    }

    override fun onUpdateError(errorResponse: Throwable) {
        Log.d(TAG,"question update fail")
        onRequestLietenerFailed.onFailed(Throwable())
    }
}