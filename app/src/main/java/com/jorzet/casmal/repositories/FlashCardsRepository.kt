package com.jorzet.casmal.repositories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.utils.Utils

class FlashCardsRepository {
    private var flashCards: MutableLiveData<MutableList<FlashCard>> = MutableLiveData()
    private var exception: MutableLiveData<Exception> = MutableLiveData()

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

        Utils.print("Set ${items.size} items")

        items.clear()
        items.addAll(list)

        this.flashCards.value = items
    }

    fun setException(ex: Exception) {
        exception.value = ex
        exception.postValue(null)
    }

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

    @NonNull
    fun getFlashCards() : LiveData<MutableList<FlashCard>> = flashCards

    @NonNull
    fun getException() : LiveData<java.lang.Exception> = exception

    fun destroy() {
        flashCards.value = null
    }
}