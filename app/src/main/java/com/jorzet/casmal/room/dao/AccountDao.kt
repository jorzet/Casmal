package com.jorzet.casmal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jorzet.casmal.models.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM tbl_accounts")
    fun getAccounts(): LiveData<List<Account>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: Account)

    @Update
    fun update(account: Account)

    @Delete
    fun delete(account: Account)
}