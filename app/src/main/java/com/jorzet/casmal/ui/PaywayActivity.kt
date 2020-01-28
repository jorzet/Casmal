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
import androidx.lifecycle.ViewModelProviders
import com.android.billingclient.api.*
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseActivity
import com.jorzet.casmal.managers.BillingManager
import com.jorzet.casmal.managers.BillingManager.OnBillingPurchasesListener
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.Payment
import com.jorzet.casmal.models.User
import com.jorzet.casmal.viewmodels.UserViewModel

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

    /**
     * View Model
     */
    private lateinit var userViewModel: UserViewModel


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

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
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

    override fun onBillingServiceReady() {
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

        /*mBillingManager.queryPurchases(SKU_TYPE, object: OnBillingPurchasesListener  {

            override fun onBillingResponseItemAlreadyOwned(purchase: Purchase) {
                Log.d("","")
            }

            override fun onBillingResponseItemNotOwned() {
                Log.d("","")
            }

        })*/
    }

    override fun onBillingServiceError() {
        Log.d(TAG, "onBillingServiceError()")
    }

    override fun onBillingServiceDisconnected() {
        Log.d(TAG, "onBillingServiceDisconnected()")
    }

    override fun onBillingResponseOk(purchases: List<Purchase?>?) {
        if (purchases != null) {
            Log.d(TAG, "onBillingResponseOk: purchases ${purchases.size}")

            var purchase: Purchase? = null

            for (purch in purchases) {
                if (purch?.purchaseState == Purchase.PurchaseState.PURCHASED) {
                    purchase = purch
                    break;
                }
            }

            if (purchase != null) {
                val user: User = userViewModel.getUser().value ?: return
                val payment: Payment = user.payment
                payment.isPremium = true
                payment.timeStamp = purchase.purchaseTime
                payment.subscription = purchase.sku
                user.payment = payment
                userViewModel.setUser(user)

                val uid = FirebaseAuth.getInstance().currentUser?.uid
                if (uid != null) {
                    FirebaseRequestManager.getInstance()
                        .insertUser(uid, object : FirebaseRequestManager.OnInsertUserListener {
                            override fun onSuccessUserInserted() {
                                Log.d(TAG, "user push success after subscription")
                            }

                            override fun onErrorUserInserted(throwable: Throwable) {
                                Log.d(TAG, "user push fail after subscription")
                            }

                        })
                }

                goBackActivity()
            }
        }
    }

    override fun onBillingResponseUserCanceled() {
        Log.d(TAG, "onBillingResponseUserCanceled()")
    }

    override fun onBillingResponseServiceUnavailable() {
        Log.d(TAG, "onBillingResponseServiceUnavailable()")
    }

    override fun onBillingResponseBillingUnavailable() {
        Log.d(TAG, "onBillingResponseBillingUnavailable()")
    }

    override fun onBillingResponseItemUnavailable() {
        Log.d(TAG, "onBillingResponseItemUnavailable()")
    }

    override fun onBillingResponseDeveloperError() {
        Log.d(TAG, "onBillingResponseDeveloperError()")
    }

    override fun onBillingResponseError() {
        Log.d(TAG, "onBillingResponseError()")
    }

    override fun onBillingResponseItemAlreadyOwned() {
        Log.d(TAG, "onBillingResponseItemAlreadyOwned()")
    }

    override fun onBillingResponseItemNotOwned() {
        Log.d(TAG, "onBillingResponseItemNotOwned()")
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: MutableList<Purchase>?) {
        Log.d(TAG, "responseCode: ${billingResult.responseCode}")
    }

    fun goBackActivity() {
        onBackPressed()
    }
}