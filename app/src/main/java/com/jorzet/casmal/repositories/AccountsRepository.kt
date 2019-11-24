package com.jorzet.casmal.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorzet.casmal.interfaces.RoomOperationListener
import com.jorzet.casmal.models.Account
import com.jorzet.casmal.room.dao.AccountDao
import com.jorzet.casmal.room.tasks.DeleteAccount
import com.jorzet.casmal.room.tasks.InsertAccount
import com.jorzet.casmal.room.tasks.UpdateAccount
import com.jorzet.casmal.utils.Utils

class AccountsRepository(private val accountDao: AccountDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    private val accounts: LiveData<List<Account>> = accountDao.getAccounts()
    private val loginFacebook: MutableLiveData<Boolean> = MutableLiveData()
    private val loginGoogle: MutableLiveData<Boolean> = MutableLiveData()

    fun insert(account: Account) {
        InsertAccount(accountDao, account, object : RoomOperationListener {
            override fun onBegin() {
                Utils.print("Begin Insert Account: ${account.userName}")
            }

            override fun onFinish() {
                Utils.print("Finish Insert Account: ${account.userName}")
            }
        }).execute()
    }

    fun update(account: Account) {
        UpdateAccount(accountDao, account, object : RoomOperationListener {
            override fun onBegin() {
                Utils.print("Begin Update Account: ${account.userName}")
            }

            override fun onFinish() {
                Utils.print("Finish Update Account: ${account.userName}")
            }
        }).execute()
    }

    fun delete(account: Account) {
        Utils.print("Delete repository")

        DeleteAccount(accountDao, account, object : RoomOperationListener {
            override fun onBegin() {
                Utils.print("Begin Delete Account: ${account.userName}")
            }

            override fun onFinish() {
                Utils.print("Finish Delete Account: ${account.userName}")
            }
        }).execute()
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