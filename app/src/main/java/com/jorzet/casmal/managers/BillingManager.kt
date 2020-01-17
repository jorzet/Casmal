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

import android.util.Log
import android.widget.Toast
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClient.BillingResponse
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.SkuDetailsParams
import com.android.billingclient.api.SkuDetailsResponseListener
import com.jorzet.casmal.ui.PaywayActivity

/**
 * BillingManager that handles all the interactions with Play Store
 * (via Billing library), maintain connection to it through BillingClient and cache
 * temporary states/data if needed.
 */
class BillingManager(activity: PaywayActivity) : PurchasesUpdatedListener {

    private val mBillingClient: BillingClient =
        BillingClient.newBuilder(activity).setListener(this).build()
    private val mActivity: PaywayActivity = activity

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
        fun onBillingResponseOk()
        fun onBillingResponseUserCanceled()
        fun onBillingResponseServiceUnavailable()
        fun onBillingResponseBillingUnavailable()
        fun onBillingResponseItemUnavailable()
        fun onBillingResponseDeveloperError()
        fun onBillingResponseError()
        fun onBillingResponseItemAlreadyOwned()
        fun onBillingResponseItemNotOwned()
    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: List<Purchase?>?) {
        Log.i(TAG, "onPurchasesUpdated() response: $responseCode")
        when (responseCode) {
            BILLING_RESPONSE_RESULT_OK -> {
                mActivity.onBillingResponseOk()
            }
            BILLING_RESPONSE_RESULT_USER_CANCELED -> {
                mActivity.onBillingResponseUserCanceled()
            }
            BILLING_RESPONSE_RESULT_SERVICE_UNAVAILABLE -> {
                mActivity.onBillingResponseServiceUnavailable()
            }
            BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE -> {
                mActivity.onBillingResponseBillingUnavailable()
            }
            BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE -> {
                mActivity.onBillingResponseItemUnavailable()
            }
            BILLING_RESPONSE_RESULT_DEVELOPER_ERROR -> {
                mActivity.onBillingResponseDeveloperError()
            }
            BILLING_RESPONSE_RESULT_ERROR -> {
                mActivity.onBillingResponseError()
            }
            BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED -> {
                mActivity.onBillingResponseItemAlreadyOwned()
            }
            BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED -> {
                mActivity.onBillingResponseItemNotOwned()
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
                override fun onBillingSetupFinished(@BillingResponse billingResponse: Int) {
                    if (billingResponse == BillingResponse.OK) {
                        Log.i(TAG, "onBillingSetupFinished() response: $billingResponse")
                        executeOnSuccess?.run()
                    } else {
                        Toast.makeText(mActivity,
                            "Failed to connect GooglePlay", Toast.LENGTH_SHORT).show()
                        Log.w(TAG, "onBillingSetupFinished() error code: $billingResponse")
                    }
                }

                override fun onBillingServiceDisconnected() {
                    Log.w(TAG, "onBillingServiceDisconnected()")
                }
            })
        }
    }

    fun querySkuDetailsAsync(
        @BillingClient.SkuType itemType: String?,
        skuList: List<String?>?, listener: SkuDetailsResponseListener
    ) { // Specify a runnable to start when connection to Billing client is established
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

    fun startPurchaseFlow(skuId: String?, billingType: String?) {
        // Specify a runnable to start when connection to Billing client is established
        val executeOnConnectedService = Runnable {
            val billingFlowParams: BillingFlowParams = BillingFlowParams.newBuilder()
                .setType(billingType)
                .setSku(skuId)
                .build()
            mBillingClient.launchBillingFlow(mActivity, billingFlowParams)
        }
        // If Billing client was disconnected, we retry 1 time and if success, execute the query
        startServiceConnectionIfNeeded(executeOnConnectedService)
    }

    fun destroy() {
        mBillingClient.endConnection()
    }
}