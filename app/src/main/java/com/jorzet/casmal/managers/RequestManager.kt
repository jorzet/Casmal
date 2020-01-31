package com.jorzet.casmal.managers

import com.jorzet.casmal.interfaces.RequestListener
import com.jorzet.casmal.utils.Utils
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header

/**
 * Bani Azarael
 * 15/02/2018.
 * Develop by CodeQuark
 * www.codequark.com
 */
open class RequestManager(private val url: String, private val listener: RequestListener<String>) : AsyncHttpClient() {
    private var params: RequestParams? = null
    private var handler: AsyncHttpResponseHandler? = null

    private fun prepare() {
        handler = object : AsyncHttpResponseHandler() {
            override fun onStart() {
                listener.onStartRequest()
            }

            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: ByteArray) {
                listener.onSuccess(String(response))
            }

            override fun onFailure(i: Int, headers: Array<Header?>?, bytes: ByteArray?, throwable: Throwable?) {
                listener.onError(throwable as Exception)
                Utils.print(throwable.toString())
            }
        }
    }

    protected fun setParams(params: RequestParams) {
        this.params = params
    }

    fun cancel() {
        cancelAllRequests(true)
    }

    fun executeGet() {
        prepare()
        if (params != null) {
            get(url, params, handler)
        } else {
            get(url, handler)
        }
    }

    fun executePost() {
        prepare()
        if (params != null) {
            post(url, params, handler)
        } else {
            post(url, handler)
        }
    }

    init {
        Utils.print("URL: $url")
    }
}