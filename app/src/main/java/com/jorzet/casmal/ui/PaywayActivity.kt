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

package com.jorzet.casmal.ui

import android.util.Log
import android.view.MenuItem
import android.view.View
import com.android.billingclient.api.*
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseActivity
import com.jorzet.casmal.managers.BillingManager

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 15/01/20.
 */

class PaywayActivity: BaseActivity(), BillingManager.OnBillingResponseListener,
    PurchasesUpdatedListener {

    companion object {
        const val TAG = "PaywayActivity"
        const val PRODUCT_ID = "casmal_monthly"
        const val SKU_TYPE =  BillingClient.SkuType.SUBS
    }

    /**
     * UI accessors
     */
    private lateinit var mGooglePayView: View
    private lateinit var mCashView: View

    /*
     * Payment
     */
    private lateinit var mBillingManager: BillingManager
    private lateinit var mSkuDetails: SkuDetails

    override fun getLayoutId(): Int {
        return R.layout.activity_payway
    }

    override fun initView() {

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }

        mGooglePayView = findViewById(R.id.rl_google_pay_container)
        mCashView = findViewById(R.id.rl_cash_container)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }

    override fun prepareComponents() {
        mGooglePayView.setOnClickListener(mGooglePayClickListener)
        mCashView.setOnClickListener(mCashClickListener)

        mBillingManager = BillingManager(this)
        createBillingClient()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBillingManager.destroy()
    }

    private val mGooglePayClickListener = View.OnClickListener {
        if (::mSkuDetails.isInitialized) {
            mBillingManager.startPurchaseFlow(mSkuDetails)
        }
    }

    private val mCashClickListener = View.OnClickListener {

    }

    override fun onBillingResponseOk() {

    }

    override fun onBillingResponseUserCanceled() {

    }

    override fun onBillingResponseServiceUnavailable() {

    }

    override fun onBillingResponseBillingUnavailable() {

    }

    override fun onBillingResponseItemUnavailable() {

    }

    override fun onBillingResponseDeveloperError() {

    }

    override fun onBillingResponseError() {

    }

    override fun onBillingResponseItemAlreadyOwned() {

    }

    override fun onBillingResponseItemNotOwned() {

    }

    private fun createBillingClient() {
        val mBillingClient = BillingClient.newBuilder(this).setListener(this).build()

        mBillingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.i(TAG, "onBillingSetupFinished() response: ${billingResult.debugMessage}")

                    val skuList = listOf(PRODUCT_ID)
                    mBillingManager.querySkuDetailsAsync(SKU_TYPE, skuList,
                        SkuDetailsResponseListener {
                                responseCode, skuDetailsList ->
                            Log.i(TAG, "response code: $responseCode ${skuDetailsList?.size}")

                            for (skuDetail in skuDetailsList) {
                                if (skuDetail.sku == PRODUCT_ID) {
                                    mSkuDetails = skuDetail
                                }
                            }
                        })
                } else {
                    Log.w(TAG, "onBillingSetupFinished() error code: ${billingResult.responseCode}")
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.w(TAG, "onBillingServiceDisconnected()")
            }
        })
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
        Log.d(TAG, "responseCode: ${billingResult.responseCode}")
    }
}