package com.jorzet.casmal.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorzet.casmal.AppExecutors
import com.jorzet.casmal.models.Account
import com.jorzet.casmal.room.dao.AccountDao
import com.jorzet.casmal.utils.Utils

class AccountsRepository(private val accountDao: AccountDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    private val accounts: LiveData<List<Account>> = accountDao.getAccounts()
    private val loginFacebook: MutableLiveData<Boolean> = MutableLiveData()
    private val loginGoogle: MutableLiveData<Boolean> = MutableLiveData()

    fun insert(account: Account) {
        AppExecutors.get().diskIO().execute {
            accountDao.insert(account)
            Utils.print("Insert Account: ${account.userName}")
        }
    }

    fun update(account: Account) {
        AppExecutors.get().diskIO().execute {
            accountDao.update(account)
            Utils.print("Update Account: ${account.userName}")
        }
    }

    fun delete(account: Account) {
        AppExecutors.get().diskIO().execute {
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

    fun getAccounts(): LiveData<List<Account>> = accounts

    fun getLoginWithFacebook(): MutableLiveData<Boolean> = loginFacebook

    fun getLoginWithGoogle(): MutableLiveData<Boolean> = loginGoogle
}