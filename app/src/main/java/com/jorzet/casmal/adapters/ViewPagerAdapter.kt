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

package com.jorzet.casmal.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jorzet.casmal.fragments.ModulesFragment
import com.jorzet.casmal.fragments.ProfileFragment
import com.jorzet.casmal.fragments.ScoresFragment
import com.jorzet.casmal.fragments.SubjectsFragment

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 14/08/19.
 */

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        var fragment = Fragment()
        when(position) {
            //TODO Fix ViewPager
            0 -> fragment = ModulesFragment()
            //1 -> fragment = SubjectsFragment()
            2 -> fragment = ScoresFragment()
            3 -> fragment = ProfileFragment()
        }

        return fragment
    }

    override fun getCount(): Int {
        return 4
    }
}