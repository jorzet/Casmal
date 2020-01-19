package com.jorzet.casmal.repositories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.utils.Utils

class FlashCardsRepository : ViewModelRepository() {
    private val flashCards: MutableLiveData<MutableList<FlashCard>> = MutableLiveData()

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
        val items: MutableList<FlashCard> = flashCards.value ?: return

        Utils.print("Set ${items.size} flashCards")
        items.forEach {
            Utils.print(it.toString())
        }

        items.clear()
        items.addAll(list)

        this.flashCards.value = items
    }

    @NonNull
    fun getFlashCards() : LiveData<MutableList<FlashCard>> = flashCards

    fun load() {
        FirebaseRequestManager.getInstance().requestFlashCards(object: FirebaseRequestManager.OnGetFlashCardListener {
            override fun onGetFlashCardsSuccess(flashCards: MutableList<FlashCard>) {
                setFlashCards(flashCards)
            }

            override fun onFlashCardFail(throwable: Throwable) {
                setException(Exception(throwable))
            }
        })
    }

    fun destroy() {
        flashCards.value = null
    }
}