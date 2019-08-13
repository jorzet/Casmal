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
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jorzet.casmal.R
import com.jorzet.casmal.fragments.ModulesFragment
import com.jorzet.casmal.fragments.ProfileFragment
import com.jorzet.casmal.fragments.ScoreFragment
import com.jorzet.casmal.fragments.SubjectsFragment

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 12/08/19.
 */

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        setInitialFragment()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.getItemId()) {
            R.id.action_modules -> fragment = ModulesFragment()
            R.id.action_subjects -> fragment = SubjectsFragment()
            R.id.action_scores -> fragment = ScoreFragment()
            R.id.action_profile -> fragment = ProfileFragment()
        }

        if (fragment != null) replaceFragment(fragment)

        return true
    }

    /**
     * Set an initial fragment in container
     */
    private fun setInitialFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, ModulesFragment())
        fragmentTransaction.commit()
    }

    /**
     * Replace current [Fragment]
     *
     * @param fragment current fragment
     */
    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}