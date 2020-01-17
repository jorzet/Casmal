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

package com.jorzet.casmal.managers

import android.content.Context
import com.jorzet.casmal.models.*

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 087/08/19.
 */

abstract class FirebaseRequestManager(context: Context) {

    companion object {
        const val TAG : String = "FirebaseRequestManager"
        /**
         * Manager constructor
         * @param context Base Activity or Fragment [Context]
         */
        fun getInstance(context: Context): FirebaseRequestManager {
            return FirebaseRequestManagerImp.getInstance(context)
        }
    }

    interface OnGetModulesListener {
        /**
         *
         */
        fun onGetModulesSuccess(modules: List<Module>)

        /**
         *
         */
        fun onGetModulesFail(throwable: Throwable)
    }

    interface OnGetSubjectsListener {
        /**
         *
         */
        fun onGetSubjectsSuccess(subjects: List<Subject>)

        /**
         *
         */
        fun onGetSubjectsFail(throwable: Throwable)
    }

    interface OnGetQuestionListener {
        /**
         *
         */
        fun onGetQuestionLoaded(question: Question)

        /**
         *
         */
        fun onGetQuestionError(throwable: Throwable)
    }

    interface OnGetUserListener {
        /**
         *
         */
        fun onGetUserLoaded(user: User?)
        /**
         *
         */
        fun onGetUserError(throwable: Throwable)
    }

    interface OnInsertUserListener {
        /**
         *
         */
        fun onSuccessUserInserted()
        /**
         *
         */
        fun onErrorUserInserted(throwable: Throwable)
    }

    interface OnPushAverageListener {
        /**
         *
         */
        fun onPushAverageSuccess()

        /**
         *
         */
        fun onPushAverageFail(throwable: Throwable)
    }

    interface OnGetFlashCardListener {
        /**
         *
         */
        fun onGetFlashCardSuccess(flashCard: FlashCard)

        /**
         *
         */
        fun onGetFlashCardsSuccess(flashCards: List<FlashCard>)

        /**
         *
         */
        fun onFlashCardFail(throwable: Throwable)
    }

    interface OnGetLevelsListener {
        /**
         *
         */
        fun onGetLevelsSuccess(levels: List<Level>)

        /**
         *
         */
        fun onGetLevelsFail(throwable: Throwable)
    }

    interface OnUpdateUserLevelListener {
        /**
         *
         */
        fun onUpdateUserLevelSuccess()

        /**
         *
         */
        fun onUpdateUserLevelFail(throwable: Throwable)
    }

    /**
     *
     */
    abstract fun requestModules(onGetModulesListener: OnGetModulesListener)

    /**
     *
     */
    abstract fun requestSubjects(onGetSubjectsListener: OnGetSubjectsListener)

    /**
     *
     */
    abstract fun requestQuestion(questionId: String, onGetQuestionsListener: OnGetQuestionListener)

    /**
     *
     */
    abstract fun requestUser(uid: String, onGetUserListener: OnGetUserListener)

    /**
     *
     */
    abstract fun insertUser(uid: String, onGetUserListener: OnInsertUserListener)

    /**
     *
     */
    abstract fun pushAverage(isExam: Boolean, average: Average, onPushAverageListener: OnPushAverageListener)

    /**
     *
     */
    abstract fun requestFlashCard(flashCardId: String, onGetFlashCardListener: OnGetFlashCardListener)

    /**
     *
     */
    abstract fun requestFlashCards(onGetFlashCardListener: OnGetFlashCardListener)

    /**
     *
     */
    abstract fun requestLevels(onGetLevelsListener: OnGetLevelsListener)

    /**
     *
     */
    abstract fun updateUserLevel(onUpdateUserLevelListener: OnUpdateUserLevelListener)

    /**
     * Destroy [FirebaseRequestManager] instance
     */
    abstract fun destroy()
}