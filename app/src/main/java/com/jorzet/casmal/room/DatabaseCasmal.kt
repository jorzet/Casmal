package com.jorzet.casmal.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jorzet.casmal.models.Account
import com.jorzet.casmal.room.dao.AccountDao

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