package com.jorzet.casmal.viewmodels

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jorzet.casmal.models.Account
import com.jorzet.casmal.repositories.AccountsRepository
import com.jorzet.casmal.room.DatabaseCasmal
import com.jorzet.casmal.room.dao.AccountDao

class AccountsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AccountsRepository
    private val accounts: LiveData<List<Account>>

    init {
        val accountDao: AccountDao = DatabaseCasmal.getDatabase(application).accountDao()
        repository = AccountsRepository(accountDao)
        accounts = repository.accounts
    }

    fun insert(account: Account) = repository.insert(account)

    fun update(account: Account) = repository.update(account)

    fun delete(account: Account) = repository.delete(account)

    @NonNull
    val list: LiveData<List<Account>> = accounts
}