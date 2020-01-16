package com.jorzet.casmal.repositories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorzet.casmal.AppExecutors
import com.jorzet.casmal.CasmalApplication
import com.jorzet.casmal.models.Account
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.models.User
import com.jorzet.casmal.room.DatabaseCasmal
import com.jorzet.casmal.room.dao.AccountDao
import com.jorzet.casmal.utils.Utils

class UserRepository(private val accountDao: AccountDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    private val account: LiveData<Account> = accountDao.getAccount()
    private val loginFacebook: MutableLiveData<Boolean> = MutableLiveData()
    private val loginGoogle: MutableLiveData<Boolean> = MutableLiveData()

    private val user: MutableLiveData<User> = MutableLiveData()
    private var flashCards: MutableLiveData<MutableList<FlashCard>> = MutableLiveData()

    companion object {
        private val accountDao: AccountDao = DatabaseCasmal.getDatabase(CasmalApplication.get()).accountDao()

        val instance: UserRepository = UserRepository(accountDao)

        init {
            Utils.print("Instance", "Instance UserRepository = " + instance.hashCode())
        }
    }

    fun addFlashCard(flashCard: FlashCard) {
        val items: MutableList<FlashCard>? = flashCards.value

        if(items != null) {
            items.add(flashCard)

            this.flashCards.value = items
        }
    }

    fun setUserFlashCards(list: MutableList<FlashCard>) {
        val items: MutableList<FlashCard>? = flashCards.value

        if(items != null) {
            items.clear()
            items.addAll(list)

            this.flashCards.value = items
        }
    }

    fun setUser(user: User) {
        this.user.value = user
    }

    @NonNull
    fun getUser() : LiveData<User> {
        if(user.value == null) {
            val user = User()
            this.user.value = user
        }

        return user
    }

    @NonNull
    fun getFlashCards() : LiveData<MutableList<FlashCard>> {
        if(flashCards.value == null) {
            val flashCards: MutableList<FlashCard> = ArrayList()
            this.flashCards.value = flashCards
        }

        return flashCards
    }

    fun insert(account: Account) {
        AppExecutors.instance.diskIO().execute {
            accountDao.insert(account)
            Utils.print("Insert Account: ${account.userName}")
        }
    }

    fun update(account: Account) {
        AppExecutors.instance.diskIO().execute {
            accountDao.update(account)
            Utils.print("Update Account: ${account.userName}")
        }
    }

    fun delete(account: Account) {
        AppExecutors.instance.diskIO().execute {
            accountDao.delete(account)
            Utils.print("Delete Account: ${account.userName}")
        }
    }

    fun loginWithFacebook() {
        loginFacebook.value = true
        loginFacebook.postValue(false)
    }

    fun loginWithGoogle() {
        loginGoogle.value = true
        loginGoogle.postValue(false)
    }

    @NonNull
    fun getAccount(): LiveData<Account> = account

    @NonNull
    fun getLoginWithFacebook(): MutableLiveData<Boolean> = loginFacebook

    @NonNull
    fun getLoginWithGoogle(): MutableLiveData<Boolean> = loginGoogle

    fun destroy() {
        user.value = null
        flashCards.value = null
    }
}