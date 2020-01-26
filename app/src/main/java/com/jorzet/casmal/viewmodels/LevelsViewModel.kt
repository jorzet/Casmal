package com.jorzet.casmal.viewmodels

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jorzet.casmal.models.Level
import com.jorzet.casmal.repositories.LevelsRepository

class LevelsViewModel : ViewModel() {
    private val mLevels: LiveData<MutableList<Level>>
    private val repository = LevelsRepository.instance

    init {
        mLevels = repository.getLevels()
    }

    @NonNull
    val levels: LiveData<MutableList<Level>> = mLevels

    fun load() {
        repository.load()
        repository.load()
    }
}