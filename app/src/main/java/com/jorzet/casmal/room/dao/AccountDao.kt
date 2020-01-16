package com.jorzet.casmal.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jorzet.casmal.models.Account

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

@Dao
interface AccountDao {
    @Query("SELECT * FROM tbl_accounts")
    fun getAccount(): LiveData<Account>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: Account)

    @Update
    fun update(account: Account)

    @Delete
    fun delete(account: Account)

    @Query("DELETE FROM tbl_accounts")
    fun deleteAll()
}