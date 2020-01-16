package com.jorzet.casmal.repositories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.utils.Utils

class FlashCardsRepository {
    private var flashCards: MutableLiveData<MutableList<FlashCard>> = MutableLiveData()

    companion object {
        val instance: FlashCardsRepository = FlashCardsRepository()

        init {
            Utils.print("Instance", "Instance FlashCardsRepository = " + instance.hashCode())
        }
    }

    init {
        val flashCards: MutableList<FlashCard> = ArrayList()

        this.flashCards.value = flashCards
    }

    fun setFlashCards(list: MutableList<FlashCard>) {
        val items: MutableList<FlashCard>? = flashCards.value

        if(items != null) {
            items.clear()
            items.addAll(list)

            this.flashCards.value = items
        }
    }

    @NonNull
    fun getFlashCards() : LiveData<MutableList<FlashCard>> = flashCards

    fun destroy() {
        flashCards.value = null
    }
}