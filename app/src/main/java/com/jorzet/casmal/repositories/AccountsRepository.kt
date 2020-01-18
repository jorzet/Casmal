/*
 * Copyright [2020] [Jorge Zepeda Tinoco]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jorzet.casmal.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jorzet.casmal.AppExecutors
import com.jorzet.casmal.models.Account
import com.jorzet.casmal.room.dao.AccountDao
import com.jorzet.casmal.utils.Utils

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

class AccountsRepository(private val accountDao: AccountDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    private val account: LiveData<Account> = accountDao.getAccount()
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

    fun getAccount(): LiveData<Account> = account

    fun getLoginWithFacebook(): MutableLiveData<Boolean> = loginFacebook

    fun getLoginWithGoogle(): MutableLiveData<Boolean> = loginGoogle
}