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

package com.jorzet.casmal.fragments

import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseFragment

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 12/08/19.
 */

class ScoresFragment: BaseFragment() {
    private lateinit var barChart1: BarChart
    private lateinit var barChart2: BarChart

    override fun getLayoutId(): Int {
        return R.layout.scores_fragment
    }

    override fun initView() {
        barChart1 = rootView.findViewById(R.id.barChart1)
        barChart2 = rootView.findViewById(R.id.barChart2)
    }

    override fun prepareComponents() {
        val entries: MutableList<BarEntry> = ArrayList()

        entries.add(BarEntry(0.0f, 2.0f)) //User
        entries.add(BarEntry(1.0f, 4.0f)) //Average
        entries.add(BarEntry(2.0f, 6.0f)) //Maxium

        val barDataSet1 = BarDataSet(entries, "Examen 1")
        val barData1 = BarData(barDataSet1)

        val barDataSet2 = BarDataSet(entries, "Examen 2")
        val barData2 = BarData(barDataSet2)

        barDataSet1.setColors(
            Color.rgb(193, 37, 82),
            Color.rgb(255, 102, 0),
            Color.rgb(245, 199, 0)
        )

        barDataSet2.setColors(
            Color.rgb(193, 37, 82),
            Color.rgb(255, 102, 0),
            Color.rgb(245, 199, 0)
        )

        barData1.barWidth = 1.0f
        barData2.barWidth = 1.0f

        barChart1.data = barData1
        barChart1.setFitBars(false)
        barChart1.setDrawBarShadow(false)
        barChart1.setDrawValueAboveBar(false)
        barChart1.setScaleEnabled(false)
        barChart1.invalidate()

        barChart2.data = barData2
        barChart2.setFitBars(false)
        barChart2.setDrawBarShadow(false)
        barChart2.setDrawValueAboveBar(false)
        barChart2.setScaleEnabled(false)
        barChart2.invalidate()
    }
}