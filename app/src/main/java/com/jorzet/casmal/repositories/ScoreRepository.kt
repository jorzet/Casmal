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

package com.jorzet.casmal.repositories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorzet.casmal.interfaces.RequestListener
import com.jorzet.casmal.models.Score
import com.jorzet.casmal.request.ScoreRequest
import com.jorzet.casmal.utils.Utils

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

class ScoreRepository: ViewModelRepository() {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    private val list: MutableLiveData<MutableList<Score>> = MutableLiveData()

    companion object {
        val instance: ScoreRepository = ScoreRepository()

        init {
            Utils.print("Instance", "Instance ScoreRepository = " + instance.hashCode())
        }
    }

    init {
        list.value = ArrayList()
    }

    @NonNull
    fun getScores() : LiveData<MutableList<Score>> = list

    fun load(userId: String) {
        Utils.print("Requesting scores for: $userId")

        val list: MutableList<Score> = this.list.value ?: return

        if(list.isNotEmpty()) {
            return
        }

        ScoreRequest.parse(userId, object : RequestListener<MutableList<Score>> {
            override fun onStartRequest() {
                updating.value = true
            }

            override fun onSuccess(response: MutableList<Score>) {
                updating.value = false
                setScores(response)
            }

            override fun onError(ex: Exception) {
                updating.value = false
                exception.value = ex
            }
        })
    }

    private fun setScores(scores: MutableList<Score>) {
        val list: MutableList<Score> = this.list.value ?: return

        list.addAll(scores)

        this.list.value = list
    }
}