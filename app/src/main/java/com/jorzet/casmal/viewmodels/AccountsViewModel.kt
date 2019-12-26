package com.jorzet.casmal.viewmodels

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jorzet.casmal.models.Account
import com.jorzet.casmal.repositories.AccountsRepository
import com.jorzet.casmal.room.DatabaseCasmal
import com.jorzet.casmal.room.dao.AccountDao

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

class AccountsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AccountsRepository
    private val accounts: LiveData<List<Account>>
    private val loginWithFacebook: LiveData<Boolean>
    private val loginWithGoogle: LiveData<Boolean>

    init {
        val accountDao: AccountDao = DatabaseCasmal.getDatabase(application).accountDao()
        repository = AccountsRepository(accountDao)
        accounts = repository.getAccounts()
        loginWithFacebook = repository.getLoginWithFacebook()
        loginWithGoogle = repository.getLoginWithGoogle()
    }

    fun insert(account: Account) = repository.insert(account)

    fun update(account: Account) = repository.update(account)

    fun delete(account: Account) = repository.delete(account)

    fun loginWithFacebook() {
        repository.loginWithFacebook()
    }

    fun loginWithGoogle() {
        repository.loginWithGoogle()
    }

    @NonNull
    val list: LiveData<List<Account>> = accounts

    @NonNull
    val loginFacebook: LiveData<Boolean> = loginWithFacebook

    @NonNull
    val loginGoogle: LiveData<Boolean> = loginWithGoogle
}