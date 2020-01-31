/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jorzet.casmal.managers

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.billingclient.api.*
import com.jorzet.casmal.ui.PaywayActivity

/**
 * BillingManager that handles all the interactions with Play Store
 * (via Billing library), maintain connection to it through BillingClient and cache
 * temporary states/data if needed.
 */
class BillingManager(private val activity: Activity, private val onBillingResponseListener: OnBillingResponseListener) : PurchasesUpdatedListener {

    private var mBillingClient: BillingClient = BillingClient.newBuilder(activity)
        .enablePendingPurchases().setListener(this).build()

    companion object {
        private const val TAG = "BillingManager"
        const val BILLING_RESPONSE_RESULT_OK = 0
        const val BILLING_RESPONSE_RESULT_USER_CANCELED = 1
        const val BILLING_RESPONSE_RESULT_SERVICE_UNAVAILABLE = 2
        const val BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3
        const val BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE = 4
        const val BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = 5
        const val BILLING_RESPONSE_RESULT_ERROR = 6
        const val BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED = 7
        const val BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = 8
    }

    init {
        startServiceConnectionIfNeeded(null)
    }

    interface OnBillingResponseListener {
        fun onBillingServiceReady()
        fun onBillingServiceError()
        fun onBillingServiceDisconnected()
        fun onBillingResponseOk(purchases: List<Purchase?>?)
        fun onBillingResponseUserCanceled()
        fun onBillingResponseServiceUnavailable()
        fun onBillingResponseBillingUnavailable()
        fun onBillingResponseItemUnavailable()
        fun onBillingResponseDeveloperError()
        fun onBillingResponseError()
        fun onBillingResponseItemAlreadyOwned()
        fun onBillingResponseItemNotOwned()
    }

    interface OnBillingPurchasesListener {
        fun onBillingResponseItemAlreadyOwned(purchase: Purchase)
        fun onBillingResponseItemNotOwned()
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase?>?) {
        Log.i(TAG, "onPurchasesUpdated() response: ${billingResult.responseCode}")
        when (billingResult.responseCode) {
            BILLING_RESPONSE_RESULT_OK -> {
                onBillingResponseListener.onBillingResponseOk(purchases)
            }
            BILLING_RESPONSE_RESULT_USER_CANCELED -> {
                onBillingResponseListener.onBillingResponseUserCanceled()
            }
            BILLING_RESPONSE_RESULT_SERVICE_UNAVAILABLE -> {
                onBillingResponseListener.onBillingResponseServiceUnavailable()
            }
            BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE -> {
                onBillingResponseListener.onBillingResponseBillingUnavailable()
            }
            BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE -> {
                onBillingResponseListener.onBillingResponseItemUnavailable()
            }
            BILLING_RESPONSE_RESULT_DEVELOPER_ERROR -> {
                onBillingResponseListener.onBillingResponseDeveloperError()
            }
            BILLING_RESPONSE_RESULT_ERROR -> {
                onBillingResponseListener.onBillingResponseError()
            }
            BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED -> {
                onBillingResponseListener.onBillingResponseItemAlreadyOwned()
            }
            BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED -> {
                onBillingResponseListener.onBillingResponseItemNotOwned()
            }
        }
    }

    /**
     * Trying to restart service connection if it's needed or just execute a request.
     *
     * Note: It's just a primitive example - it's up to you to implement a real retry-policy.
     * @param executeOnSuccess This runnable will be executed once the connection to the Billing
     * service is restored.
     */
    private fun startServiceConnectionIfNeeded(executeOnSuccess: Runnable?) {
        if (mBillingClient.isReady) {
            executeOnSuccess?.run()
        } else {
            mBillingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        Log.i(TAG, "onBillingSetupFinished() response: ${billingResult.responseCode}")
                        executeOnSuccess?.run()
                        onBillingResponseListener.onBillingServiceReady()
                    } else {
                        Toast.makeText(activity,
                            "Failed to connect GooglePlay", Toast.LENGTH_SHORT).show()
                        Log.w(TAG, "onBillingSetupFinished() error code: ${billingResult.responseCode}")
                        onBillingResponseListener.onBillingServiceError()
                    }
                }

                override fun onBillingServiceDisconnected() {
                    Log.w(TAG, "onBillingServiceDisconnected()")
                    onBillingResponseListener.onBillingServiceDisconnected()
                }
            })
        }
    }

    fun querySkuDetailsAsync(@BillingClient.SkuType itemType: String?, skuList: List<String?>?,
                             listener: SkuDetailsResponseListener) {
        // Specify a runnable to start when connection to Billing client is established
        val executeOnConnectedService = Runnable {
            val skuDetailsParams: SkuDetailsParams = SkuDetailsParams.newBuilder()
                .setSkusList(skuList).setType(itemType).build()
            mBillingClient.querySkuDetailsAsync(skuDetailsParams) {
                    responseCode, skuDetailsList ->
                listener.onSkuDetailsResponse(responseCode, skuDetailsList) }
        }
        // If Billing client was disconnected, we retry 1 time and if success, execute the query
        startServiceConnectionIfNeeded(executeOnConnectedService)
    }

    fun queryPurchases(itemType: String?, onBillingPurchasesListener: OnBillingPurchasesListener) {
        val executeOnConnectedService = Runnable {
            val purchaseResult = mBillingClient.queryPurchases(itemType)
            val purchaseList = purchaseResult.purchasesList
            if (purchaseResult.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                if (purchaseList.isNotEmpty()) {
                    for (purchase in purchaseList) {
                        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                            onBillingPurchasesListener.onBillingResponseItemAlreadyOwned(purchase)
                        } else {
                            onBillingPurchasesListener.onBillingResponseItemNotOwned()
                        }
                    }
                } else {
                    onBillingPurchasesListener.onBillingResponseItemNotOwned()
                }
            }
        }
        // If Billing client was disconnected, we retry 1 time and if success, execute the query
        startServiceConnectionIfNeeded(executeOnConnectedService)
    }

    fun startPurchaseFlow(skuDetails: SkuDetails) {
        // Specify a runnable to start when connection to Billing client is established
        val executeOnConnectedService = Runnable {
            val billingFlowParams: BillingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build()
            mBillingClient.launchBillingFlow(activity, billingFlowParams)
        }
        // If Billing client was disconnected, we retry 1 time and if success, execute the query
        startServiceConnectionIfNeeded(executeOnConnectedService)
    }

    fun destroy() {
        mBillingClient.endConnection()
    }
}