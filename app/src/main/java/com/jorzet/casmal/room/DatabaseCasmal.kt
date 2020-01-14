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

package com.jorzet.casmal.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jorzet.casmal.models.Account
import com.jorzet.casmal.room.dao.AccountDao

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

@Database(entities = [Account::class], version = RoomConstants.databaseVersion, exportSchema = false)
abstract class DatabaseCasmal: RoomDatabase() {
    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile
        private var instance: DatabaseCasmal? = null

        fun getDatabase(application: Application): DatabaseCasmal {
            if (instance != null) {
                return instance as DatabaseCasmal
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    application.applicationContext,
                    DatabaseCasmal::class.java,
                    RoomConstants.databaseName
                ).build()

                this.instance = instance

                return instance
            }
        }
    }
}