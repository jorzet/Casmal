package com.jorzet.casmal.fragments

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
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
    private lateinit var barChart: BarChart

    override fun getLayoutId(): Int {
        return R.layout.scores_fragment
    }

    override fun getFragmentActivity(): FragmentActivity {
        return this.activity!!
    }

    override fun initView() {
        barChart = rootView.findViewById(R.id.barChart)
    }

    override fun prepareComponents() {
        val entries: MutableList<BarEntry> = ArrayList()

        entries.add(BarEntry(0.0f, 2.0f))
        entries.add(BarEntry(1.0f, 4.0f))
        entries.add(BarEntry(2.0f, 6.0f))
        entries.add(BarEntry(3.0f, 8.0f))
        entries.add(BarEntry(4.0f, 3.0f))
        entries.add(BarEntry(5.0f, 1.0f))

        val barDataSet = BarDataSet(entries, "Grafica de barras")
        val barData = BarData(barDataSet)

        barDataSet.setColors(
            Color.rgb(193, 37, 82),
            Color.rgb(255, 102, 0),
            Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31),
            Color.rgb(179, 100, 53)
        )

        barData.barWidth = 0.9f
        barChart.data = barData
        barChart.setFitBars(false)
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(false)
        barChart.setScaleEnabled(false)
        barChart.invalidate()
    }
}