package com.jorzet.casmal.viewmodels

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.repositories.FlashCardsRepository

class FlashCardsViewModel : ViewModel() {
    private val mFlashCards: LiveData<MutableList<FlashCard>>

    init {
        val repository = FlashCardsRepository.instance

        mFlashCards = repository.getFlashCards()
    }

    @NonNull
    val flashCards: LiveData<MutableList<FlashCard>> = mFlashCards
}