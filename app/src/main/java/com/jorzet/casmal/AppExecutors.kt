package com.jorzet.casmal

import android.os.Handler
import android.os.Looper
import com.jorzet.casmal.utils.Utils
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors private constructor(
    private val diskIO: Executor = Executors.newSingleThreadExecutor(),
    private val networkIO: Executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS),
    private val mainThread: Executor = MainThreadExecutor()
) {
    companion object {
        @Volatile
        private var instance: AppExecutors? = null

        private const val NUMBER_OF_THREADS = 4

        fun get(): AppExecutors {
            if (instance == null) {
                synchronized(AppExecutors::class.java) {
                    if (instance == null) {
                        instance = AppExecutors()
                    }
                }
            }

            Utils.print("Instance", "Instance AppExecutors = " + instance.hashCode())
            return instance!!
        }
    }

    fun diskIO(): Executor = diskIO

    fun networkIO(): Executor = networkIO

    fun mainThread(): Executor = mainThread

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(
            Looper.getMainLooper()
        )

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}