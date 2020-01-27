package com.jorzet.casmal.ui

import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseActivity


class PaymentActivity: BaseActivity(), BillingProcessor.IBillingHandler {

    companion object {
        const val TAG = "PaymentActivity"
        private const val PRODUCT_ID = "casmal_monthly"
        private const val MERCHANT_ID = "00988406257569020779"
        private const val licenseKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkbsqwfJpBmMa0cOR8vNzwvnY0XyTzGirT11u0Cs63tAPWL5GBSxasO27uHdKuGRbw78Lxvo3kcF9OUiK+m+fK0GhscTsyfF5FHJzeB0fS97aaO409R/7eKz3Iy4ifbfLicLHl28ajMGDbzWZzKR+UMDNqQVi6IqNyFF9pTrWLC5IurYEKtFi0vyGUVgb5n1eGlUkRMIXKIsubHtqqfEzSKZtbsWf3QO5Vum5UMxvvHwXmHFuYdRr+SUkfbGB1TmsMG/yozYbY3u2HmuLsNnZgKmfc3OhivrBvbHa44bFfLdNDUek6ZpJ+Iao6r26U2sPXzYkW9kz99P2y3XIdjrGYwIDAQAB"
    }

    /**
     * UI accessors
     */
    private lateinit var mGooglePayView: View
    private lateinit var mCashView: View

    /**
     * Payment
     */
    private var bp: BillingProcessor? = null
    private var readyToPurchase = false

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

        bp = BillingProcessor(this, licenseKey, MERCHANT_ID, this)
        bp!!.initialize()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }

    override fun prepareComponents() {
        mGooglePayView.setOnClickListener(mGooglePayClickListener)

        val isAvailable = BillingProcessor.isIabServiceAvailable(this)
        if (isAvailable && readyToPurchase) { // continue
            val skuDetails1 = bp?.getPurchaseListingDetails("casmal_subscription");
            val skuDetails2 = bp?.getSubscriptionListingDetails(PRODUCT_ID);
            Log.d("","")
        }

    }


    override fun onDestroy() {
        bp?.release()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val success: Boolean? = bp?.handleActivityResult(requestCode, resultCode, data)
        if (success != null && !success)
            super.onActivityResult(requestCode, resultCode, data)
    }

    private val mGooglePayClickListener = View.OnClickListener {


        val isAvailable = BillingProcessor.isIabServiceAvailable(this)
        if (isAvailable && readyToPurchase) { // continue

            val skuDetails1 = bp?.getPurchaseListingDetails("casmal_subscription");
            val skuDetails2 = bp?.getSubscriptionListingDetails(PRODUCT_ID);
            Log.d("","")


            val success = bp?.consumePurchase(PRODUCT_ID);
            Log.d(TAG, "was success $success")
        }
    }

    override fun onBillingInitialized() {
        readyToPurchase = true;
    }

    override fun onPurchaseHistoryRestored() {

    }

    override fun onProductPurchased(productId: String, details: TransactionDetails?) {

    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {

    }
}