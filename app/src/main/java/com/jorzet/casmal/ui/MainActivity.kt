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

import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.android.billingclient.api.Purchase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.jorzet.casmal.R
import com.jorzet.casmal.adapters.ViewPagerAdapter
import com.jorzet.casmal.base.BaseActivity
import com.jorzet.casmal.dialogs.AlreadyPremiumDialog
import com.jorzet.casmal.managers.BillingManager
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.Payment
import com.jorzet.casmal.models.User
import com.jorzet.casmal.viewmodels.UserViewModel

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 12/08/19.
 */

class MainActivity: BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    ViewPager.OnPageChangeListener, BillingManager.OnBillingPurchasesListener,
    BillingManager.OnBillingResponseListener {

    /**
     * UI accessors
     */
    private lateinit var mViewPager: ViewPager
    private lateinit var mBottomNavigationView: BottomNavigationView
    private lateinit var mCoordinatorView : View
    private lateinit var mPrevMenuItem: MenuItem

    /**
     * Adapter
     */
    private lateinit var mViewPagerAdapter: ViewPagerAdapter

    /*
    * Attributes
    */
    private var doubleBackToExitPressedOnce : Boolean = false
    private val timeDelayExitBar: Int = 2000

    /*
     * Payment
     */
    private lateinit var mBillingManager: BillingManager

    /**
     * View Model
     */
    private lateinit var userViewModel: UserViewModel

    /**
     * Constants
     */
    companion object {
        const val TAG = "MainActivity"
        const val PREMIUM_RESULT_CODE = 0x01
        const val ALREADY_PREMIUM_CODE = 0x02
        const val IS_PREMIUM_EXTRA = "is_premium"

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        mViewPager = findViewById(R.id.pager)
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view)
        mCoordinatorView = findViewById(R.id.coordinator_view)
    }

    override fun prepareComponents() {
        mViewPager.addOnPageChangeListener(this)
        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        mViewPager.adapter = mViewPagerAdapter

        mBottomNavigationView.setOnNavigationItemSelectedListener(this)


        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        mBillingManager = BillingManager(this, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data?.extras?.getBoolean(IS_PREMIUM_EXTRA) == true) {
            AlreadyPremiumDialog.newInstance().show(supportFragmentManager, "already_dialog")
        }

    }

    /**
     * Override method, to shows [Snackbar] when user wants to exit
     */
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        Snackbar.make(mCoordinatorView,
            R.string.snackbar_message_text,
            timeDelayExitBar)
            .show()

        doubleBackToExitPressedOnce = true

        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_modules -> mViewPager.currentItem = 0
            R.id.action_subjects -> mViewPager.currentItem = 1
            R.id.action_scores -> mViewPager.currentItem = 2
            R.id.action_profile -> mViewPager.currentItem = 3
        }
        return true
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (::mPrevMenuItem.isInitialized) {
            mPrevMenuItem.isChecked = false
        } else {
            mBottomNavigationView.menu.getItem(0).isChecked = false
        }

        mBottomNavigationView.menu.getItem(position).isChecked = true
        mPrevMenuItem = mBottomNavigationView.menu.getItem(position)
    }

    override fun onPageSelected(position: Int) {

    }

    override fun onBillingResponseItemAlreadyOwned(purchase: Purchase) {

    }

    override fun onBillingResponseItemNotOwned() {
        val user: User = userViewModel.getUser().value ?: return
        val purchase: Purchase? = userViewModel.getPurchase().value
        val payment: Payment = user.payment
        payment.isPremium = false
        payment.timeStamp = 0
        payment.subscription = purchase?.sku?: ""
        user.payment = payment
        userViewModel.setUser(user)


        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            FirebaseRequestManager.getInstance()
                .insertUser(uid, user, object : FirebaseRequestManager.OnInsertUserListener {
                    override fun onSuccessUserInserted() {
                        Log.d(TAG, "user push success after subscription")
                    }

                    override fun onErrorUserInserted(throwable: Throwable) {
                        Log.d(TAG, "user push fail after subscription")
                    }

                })
        }
    }

    override fun onBillingServiceReady() {
        mBillingManager.queryPurchases(PaywayActivity.SKU_TYPE, this)
    }

    override fun onBillingServiceError() {

    }

    override fun onBillingServiceDisconnected() {

    }

    override fun onBillingResponseOk(purchases: List<Purchase?>?) {

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
}