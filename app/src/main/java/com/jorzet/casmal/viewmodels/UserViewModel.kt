package com.jorzet.casmal.viewmodels

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jorzet.casmal.models.Account
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.models.User
import com.jorzet.casmal.repositories.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository.instance
    private val mUser: LiveData<User>
    private val mFlashCards: LiveData<MutableList<FlashCard>>
    private val mAccount: LiveData<Account>
    private val loginWithFacebook: LiveData<Boolean>
    private val loginWithGoogle: LiveData<Boolean>

    init {
        mUser = repository.getUser()
        mFlashCards = repository.getFlashCards()

        mAccount = repository.getAccount()
        loginWithFacebook = repository.getLoginWithFacebook()
        loginWithGoogle = repository.getLoginWithGoogle()
    }

    @NonNull
    val user: LiveData<User> = mUser

    @NonNull
    val flashCards: LiveData<MutableList<FlashCard>> = mFlashCards

    @NonNull
    val account: LiveData<Account> = mAccount

    @NonNull
    val loginFacebook: LiveData<Boolean> = loginWithFacebook

    @NonNull
    val loginGoogle: LiveData<Boolean> = loginWithGoogle

    fun insert(account: Account) = repository.insert(account)

    fun update(account: Account) = repository.update(account)

    fun delete(account: Account) = repository.delete(account)

    fun loginWithFacebook() {
        repository.loginWithFacebook()
    }

    fun loginWithGoogle() {
        repository.loginWithGoogle()
    }

    fun addFlashCard(flashCard: FlashCard) {
        repository.addFlashCard(flashCard)
    }
}