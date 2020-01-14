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
import com.jorzet.casmal.request.*

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 087/08/19.
 */

class FirebaseRequestManagerImp(context: Context): FirebaseRequestManager(context) {
    companion object {
        /**
         * Manager static instance
         */
        private var sInstance: FirebaseRequestManagerImp? = null

        /**
         * Creates a [FirebaseRequestManager] implementation instance
         *
         * @param context Base Activity or Fragment [Context]
         * @return A [FirebaseRequestManager] instance
         */
        fun getInstance(context: Context): FirebaseRequestManager {
            if (sInstance == null) {
                synchronized(FirebaseRequestManager::class.java) {
                    if (sInstance == null) {
                        sInstance = FirebaseRequestManagerImp(context)
                    }
                }
            }
            return sInstance!!
        }
    }

    override fun destroy() {
        sInstance = null
    }

    override fun requestQuestion(questionId: String, onGetQuestionsListener: OnGetQuestionListener) {
        val questionsRequest = QuestionsRequest(questionId)

        questionsRequest.setOnRequestSuccess(object: AbstractDatabase.OnRequestListenerSuccess<Question> {
            override fun onSuccess(result: Question) {
                onGetQuestionsListener.onGetQuestionLoaded(result)
            }
        })

        questionsRequest.setOnRequestFailed(object: AbstractDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onGetQuestionsListener.onGetQuestionError(throwable)
            }
        })

        questionsRequest.request()
    }

    override fun pushAverage(isExam: Boolean, average: Average, onPushAverageListener: OnPushAverageListener) {
        val pushAverageRequest =  PushAverageRequets(isExam, average)

        pushAverageRequest.setOnRequestSuccess(object: AbstractDatabase.OnRequestListenerSuccess<Boolean> {
            override fun onSuccess(result: Boolean) {
                onPushAverageListener.onPushAverageSuccess()
            }
        })

        pushAverageRequest.setOnRequestFailed(object: AbstractDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onPushAverageListener.onPushAverageFail(throwable)
            }
        })

        pushAverageRequest.update()
    }

    override fun requestFlashCard(flashCardId: String, onGetFlashCardListener: OnGetFlashCardListener) {
        val flashCardRequest = FlashCardsRequest(flashCardId)

        flashCardRequest.setOnRequestSuccess(object : AbstractDatabase.OnRequestListenerSuccess<List<FlashCard>> {
            override fun onSuccess(result: List<FlashCard>) {
                onGetFlashCardListener.onGetFlashCardSuccess(result[0])
            }
        })

        flashCardRequest.setOnRequestFailed(object: AbstractDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onGetFlashCardListener.onFlashCardFail(throwable)
            }
        })

        flashCardRequest.request()
    }

    override fun requestFlashCards(onGetFlashCardListener: OnGetFlashCardListener) {
        val flashCardsRequest = FlashCardsRequest()

        flashCardsRequest.setOnRequestSuccess(object: AbstractDatabase.OnRequestListenerSuccess<List<FlashCard>> {
            override fun onSuccess(result: List<FlashCard>) {
                onGetFlashCardListener.onGetFlashCardsSuccess(result)
            }
        })

        flashCardsRequest.setOnRequestFailed(object: AbstractDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onGetFlashCardListener.onFlashCardFail(throwable)
            }
        })

        flashCardsRequest.request()
    }

    override fun requestLevels(onGetLevelsListener: OnGetLevelsListener) {
        val levelsRequest = LevelsRequest()

        levelsRequest.setOnRequestSuccess(object: AbstractDatabase.OnRequestListenerSuccess<List<Level>> {
            override fun onSuccess(result: List<Level>) {
                onGetLevelsListener.onGetLevelsSuccess(result)
            }
        })

        levelsRequest.setOnRequestFailed(object: AbstractDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onGetLevelsListener.onGetLevelsFail(throwable)
            }
        })

        levelsRequest.request()
    }

    override fun updateUserLevel(onUpdateUserLevelListener: OnUpdateUserLevelListener) {
        val pushLevelUpRequest = PushLevelUpRequest()

        pushLevelUpRequest.setOnRequestSuccess(object: AbstractDatabase.OnRequestListenerSuccess<Boolean> {
            override fun onSuccess(result: Boolean) {
                onUpdateUserLevelListener.onUpdateUserLevelSuccess()
            }
        })

        pushLevelUpRequest.setOnRequestFailed(object: AbstractDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onUpdateUserLevelListener.onUpdateUserLevelFail(throwable)
            }
        })

        pushLevelUpRequest.update()
    }

    override fun requestModules(onGetModulesListener: OnGetModulesListener) {
        val modulesRequest = ModulesRequest()

        modulesRequest.setOnRequestSuccess(object: AbstractDatabase.OnRequestListenerSuccess<List<Module>> {
            override fun onSuccess(result: List<Module>) {
                onGetModulesListener.onGetModulesSuccess(result)
            }
        })

        modulesRequest.setOnRequestFailed(object: AbstractDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onGetModulesListener.onGetModulesFail(throwable)
            }
        })

        modulesRequest.request()
    }

    override fun requestSubjects(onGetSubjectsListener: OnGetSubjectsListener) {
        val subjectsRequest = SubjectsRequest()

        subjectsRequest.setOnRequestSuccess(object: AbstractDatabase.OnRequestListenerSuccess<List<Subject>> {
            override fun onSuccess(result: List<Subject>) {
                onGetSubjectsListener.onGetSubjectsSuccess(result)
            }
        })

        subjectsRequest.setOnRequestFailed(object: AbstractDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onGetSubjectsListener.onGetSubjectsFail(throwable)
            }
        })

        subjectsRequest.request()
    }

    override fun requestUser(uid: String, onGetUserListener: OnGetUserListener) {
        val usersRequest = UserRequest(uid)

        usersRequest.setOnRequestSuccess(object : AbstractDatabase.OnRequestListenerSuccess<User?> {
            override fun onSuccess(result: User?) {
                onGetUserListener.onGetUserLoaded(result)
            }
        })

        usersRequest.setOnRequestFailed(object : AbstractDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onGetUserListener.onGetUserError(throwable)
            }
        })

        usersRequest.request()
    }

    override fun insertUser(uid: String, onGetUserListener: OnInsertUserListener) {
        val insertUserRequest = PushUserRequest(uid)

        insertUserRequest.setOnRequestSuccess(object : AbstractDatabase.OnRequestListenerSuccess<Boolean> {
            override fun onSuccess(result: Boolean) {
                onGetUserListener.onSuccessUserInserted()
            }
        })

        insertUserRequest.setOnRequestFailed(object : AbstractDatabase.OnRequestListenerFailed {
            override fun onFailed(throwable: Throwable) {
                onGetUserListener.onErrorUserInserted(throwable)
            }
        })

        insertUserRequest.update()
    }
}