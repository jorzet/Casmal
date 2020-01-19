package com.jorzet.casmal.repositories

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class ViewModelRepository {
    open val exception = MutableLiveData<Exception>()
    open val connection = MutableLiveData<Boolean>()

    @NonNull
    fun getException() : LiveData<Exception> = exception

    @NonNull
    fun getConnection() : LiveData<Boolean> = connection

    fun setConnection(connection: Boolean) {
        this.connection.value = connection
        this.connection.postValue(false)
    }

    fun setException(ex: Exception?) {
        exception.value = ex
        exception.postValue(null)
    }
}