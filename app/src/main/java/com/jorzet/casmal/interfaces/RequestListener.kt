package com.jorzet.casmal.interfaces

interface RequestListener<T> {
    fun onStartRequest() {}
    fun onSuccess(response: T) {}
    fun onError(ex: Exception) {}
}