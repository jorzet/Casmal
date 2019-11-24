/*
 * Copyright [2019] [Jorge Zepeda Tinoco]
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

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.jorzet.casmal.R
import com.jorzet.casmal.adapters.ViewPagerAdapter

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 12/08/19.
 */

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewPager = findViewById(R.id.pager)
        mBottomNavigationView = findViewById(R.id.bottom_navigation_view)
        mCoordinatorView = findViewById(R.id.coordinator_view)

        mViewPager.addOnPageChangeListener(this)
        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        mViewPager.adapter = mViewPagerAdapter

        mBottomNavigationView.setOnNavigationItemSelectedListener(this)
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
}