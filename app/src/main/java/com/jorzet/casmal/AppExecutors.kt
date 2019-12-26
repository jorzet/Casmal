package com.jorzet.casmal

import android.os.Handler
import android.os.Looper
import com.jorzet.casmal.utils.Utils
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 26/12/19
 */

class AppExecutors private constructor(
    private val diskIO: Executor = Executors.newSingleThreadExecutor(),
    private val networkIO: Executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS),
    private val mainThread: Executor = MainThreadExecutor()
) {
    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(
            Looper.getMainLooper()
        )

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

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
}