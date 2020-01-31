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

package com.jorzet.casmal.viewmodels

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jorzet.casmal.models.Score
import com.jorzet.casmal.repositories.ScoreRepository

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

class ScoreViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ScoreRepository = ScoreRepository.instance
    private val scores: LiveData<MutableList<Score>>
    private val updating: LiveData<Boolean>
    private val exception: LiveData<Exception>

    init {
        scores = repository.getScores()
        updating = repository.getUpdating()
        exception = repository.getException()
    }

    @NonNull
    fun getScores(): LiveData<MutableList<Score>> = scores

    @NonNull
    fun isUpdating(): LiveData<Boolean> = updating

    @NonNull
    fun getException(): LiveData<Exception> = exception

    fun load(userId: String) {
        repository.load(userId)
    }
}