package com.jorzet.casmal.fragments

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.FirebaseAuth
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseFragment
import com.jorzet.casmal.models.Score
import com.jorzet.casmal.viewmodels.ScoreViewModel

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 12/08/19.
 */

class ScoresFragment: BaseFragment() {
    private lateinit var barChart1: BarChart
    private lateinit var barChart2: BarChart
    private lateinit var viewModel: ScoreViewModel
    private val entriesE1: MutableList<BarEntry> = ArrayList()
    private val entriesE2: MutableList<BarEntry> = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.scores_fragment
    }

    override fun initView() {
        barChart1 = rootView.findViewById(R.id.barChart1)
        barChart2 = rootView.findViewById(R.id.barChart2)
    }

    override fun prepareComponents() {
        viewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)

        viewModel.getScores().observe(this, Observer {
            for (score: Score in it) {
                if(score.examId == "e1") {
                    entriesE1.clear()
                    entriesE1.add(BarEntry(score.user.toFloat(), 2.0f)) //User
                    entriesE1.add(BarEntry(score.average.toFloat(), 4.0f)) //Average
                    entriesE1.add(BarEntry(score.best.toFloat(), 6.0f)) //Maxium
                } else if(score.examId == "e2") {
                    entriesE2.clear()
                    entriesE2.add(BarEntry(score.user.toFloat(), 2.0f)) //User
                    entriesE2.add(BarEntry(score.average.toFloat(), 4.0f)) //Average
                    entriesE2.add(BarEntry(score.best.toFloat(), 6.0f)) //Maxium
                }
            }
        })

        viewModel.isUpdating().observe(this, Observer {

        })

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if(user != null) {
            viewModel.load(user.uid)
        }
    }

    fun setGraph() {
        val barDataSet1 = BarDataSet(entriesE1, "Examen 1")
        val barData1 = BarData(barDataSet1)

        val barDataSet2 = BarDataSet(entriesE2, "Examen 2")
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