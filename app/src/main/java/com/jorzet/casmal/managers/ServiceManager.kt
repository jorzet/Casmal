package com.jorzet.casmal.managers

import com.jorzet.casmal.models.User
import com.jorzet.casmal.utils.Utils

class ServiceManager {

    var user: User? = null

    companion object {
        @Volatile
        private var instance: ServiceManager? = null

        fun getInstance(): ServiceManager {
            if (instance != null) {
                return instance as ServiceManager
            }

            synchronized(this) {
                val instance = ServiceManager()
                this.instance = instance

                return instance
            }
        }

        init {
            Utils.print("Instance", "Instance ImageManager = " + instance.hashCode())
        }
    }
}