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

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.billingclient.api.Purchase
import com.jorzet.casmal.AppExecutors
import com.jorzet.casmal.models.Account
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.models.User
import com.jorzet.casmal.room.dao.AccountDao
import com.jorzet.casmal.utils.Utils

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

class UserRepository {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    private val user: MutableLiveData<User> = MutableLiveData()
    private val purchase: MutableLiveData<Purchase> = MutableLiveData()
    private val flashCards: MutableLiveData<MutableList<FlashCard>> = MutableLiveData()
    private val loginFacebook: MutableLiveData<Boolean> = MutableLiveData()
    private val loginGoogle: MutableLiveData<Boolean> = MutableLiveData()

    companion object {
        val instance: UserRepository = UserRepository()

        init {
            Utils.print("Instance", "Instance UserRepository = " + instance.hashCode())
        }
    }

    init {
        user.value = User()
        flashCards.value = ArrayList()
    }

    @NonNull
    fun getAccount(accountDao: AccountDao): LiveData<Account> = accountDao.getAccount()

    @NonNull
    fun getFlashCards() : LiveData<MutableList<FlashCard>> = flashCards

    @NonNull
    fun getUser() : LiveData<User> = user

    @NonNull
    fun getPurchase(): LiveData<Purchase> = purchase

    fun insert(accountDao: AccountDao, account: Account) {
        AppExecutors.get().diskIO().execute {
            accountDao.insert(account)
            Utils.print("Insert Account: ${account.userName}")
        }
    }

    fun update(accountDao: AccountDao, account: Account) {
        AppExecutors.get().diskIO().execute {
            accountDao.update(account)
            Utils.print("Update Account: ${account.userName}")
        }
    }

    fun delete(accountDao: AccountDao, account: Account) {
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

    fun setUser(user: User) {
        this.user.value = user
    }

    fun setPurchase(purchase: Purchase) {
        this.purchase.value = purchase
    }

    fun updateFlashCards() {
        val user: User = user.value ?: return
        val flashCards = FlashCardsRepository.instance.getFlashCards().value ?: return

        val items = this.flashCards.value ?: return
        items.clear()

        for(i in 0..user.level) {
            val entry = "f$i"

            for (flashCard in flashCards) {
                if (flashCard.id == entry) {
                    Utils.print("Adding flashCard: ${flashCard.id}")
                    items.add(flashCard)
                }
            }
        }

        Utils.print("Set ${items.size} user flashCards")
        this.flashCards.value = items
    }

    fun getLoginWithFacebook(): MutableLiveData<Boolean> = loginFacebook

    fun getLoginWithGoogle(): MutableLiveData<Boolean> = loginGoogle
}