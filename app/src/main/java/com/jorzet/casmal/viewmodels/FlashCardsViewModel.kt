package com.jorzet.casmal.viewmodels

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.repositories.FlashCardsRepository
import java.lang.Exception

class FlashCardsViewModel : ViewModel() {
    private val mFlashCards: LiveData<MutableList<FlashCard>>
    private val mException: LiveData<Exception>
    private val repository: FlashCardsRepository = FlashCardsRepository.instance

    init {
        mFlashCards = repository.getFlashCards()
        mException = repository.getException()
    }

    @NonNull
    val flashCards: LiveData<MutableList<FlashCard>> = mFlashCards

    @NonNull
    val exception: LiveData<Exception> = mException

    fun load() {
        repository.load()
        repository.load()
    }
}