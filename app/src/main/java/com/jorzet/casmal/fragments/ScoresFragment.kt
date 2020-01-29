package com.jorzet.casmal.fragments

import android.graphics.Color
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.FirebaseAuth
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseFragment
import com.jorzet.casmal.models.Score
import com.jorzet.casmal.utils.Utils
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
            Utils.print("Scores: ${it.size}")
            val max: Float
            var max1 = 0.0f
            var max2 = 0.0f

            for (score: Score in it) {
                Utils.print("ItemScore examId: ${score.examId}")

                if(score.examId == "e1") {
                    entriesE1.clear()
                    entriesE1.add(BarEntry(0.0f, score.user.toFloat())) //User
                    entriesE1.add(BarEntry(4.0f, score.best.toFloat())) //Best
                    entriesE1.add(BarEntry(2.0f, score.average.toFloat())) //Average

                    max1 = score.best.toFloat()
                } else if(score.examId == "e2") {
                    entriesE2.clear()
                    entriesE2.add(BarEntry(0.0f, score.user.toFloat())) //User
                    entriesE2.add(BarEntry(4.0f, score.best.toFloat())) //Best
                    entriesE2.add(BarEntry(2.0f, score.average.toFloat())) //Average

                    max2 = score.best.toFloat()
                }
            }

            max = if(max1 > max2) {
                max1
            } else {
                max2
            }

            setGraph(max)
        })

        viewModel.isUpdating().observe(this, Observer {

        })

        viewModel.getException().observe(this, Observer {
            if(it != null) {
                Utils.print("Exception: $it")
            }
        })

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if(user != null) {
            viewModel.load(user.uid)
        }
    }

    private fun setGraph(max: Float) {
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

        val yAxis1: YAxis = barChart1.axisLeft
        yAxis1.textSize = 12f // set the text size
        yAxis1.axisMinimum = 0f // start at zero
        yAxis1.axisMaximum = max // the axis maximum is 100
        yAxis1.textColor = Color.BLACK
        yAxis1.granularity = 1f // interval 1
        yAxis1.setLabelCount(6, true) // force 6 labels

        val yAxis2: YAxis = barChart2.axisLeft
        yAxis2.textSize = 12f // set the text size
        yAxis2.axisMinimum = 0f // start at zero
        yAxis2.axisMaximum = max // the axis maximum is 100
        yAxis2.textColor = Color.BLACK
        yAxis2.granularity = 1f // interval 1
        yAxis2.setLabelCount(6, true) // force 6 labels
    }
}