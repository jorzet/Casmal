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

package com.jorzet.casmal.viewmodels

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jorzet.casmal.models.Account
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.models.User
import com.jorzet.casmal.repositories.UserRepository
import com.jorzet.casmal.room.DatabaseCasmal
import com.jorzet.casmal.room.dao.AccountDao

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val accountDao: AccountDao = DatabaseCasmal.getDatabase(application).accountDao()
    private val repository: UserRepository = UserRepository.instance
    private val mAccount: LiveData<Account>
    private val mUser: LiveData<User>
    private val mFlashCards: LiveData<MutableList<FlashCard>>
    private val loginWithFacebook: LiveData<Boolean>
    private val loginWithGoogle: LiveData<Boolean>

    init {
        mAccount = repository.getAccount(accountDao)
        mUser = repository.getUser()
        mFlashCards = repository.getFlashCards()
        loginWithFacebook = repository.getLoginWithFacebook()
        loginWithGoogle = repository.getLoginWithGoogle()
    }

    fun insert(account: Account) = repository.insert(accountDao, account)

    fun update(account: Account) = repository.update(accountDao, account)

    fun delete(account: Account) = repository.delete(accountDao, account)

    fun loginWithFacebook() {
        repository.loginWithFacebook()
    }

    fun loginWithGoogle() {
        repository.loginWithGoogle()
    }

    @NonNull
    fun getAccount(): LiveData<Account> = mAccount

    @NonNull
    fun getUser(): LiveData<User> = mUser

    @NonNull
    fun getFlashCards(): LiveData<MutableList<FlashCard>> = mFlashCards

    @NonNull
    val loginFacebook: LiveData<Boolean> = loginWithFacebook

    @NonNull
    val loginGoogle: LiveData<Boolean> = loginWithGoogle

    fun setUser(user: User) {
        repository.setUser(user)
    }

    fun updateFlashCards() {
        repository.updateFlashCards()
    }
}