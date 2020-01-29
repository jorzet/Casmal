package com.jorzet.casmal.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.jorzet.casmal.R
import com.jorzet.casmal.models.Score
import com.jorzet.casmal.utils.Utils
import com.jorzet.casmal.viewholders.GraphHolder
import com.jorzet.casmal.viewholders.ViewHolder


class GraphAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var list: MutableList<Score> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraphHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_graph, parent, false)
        return GraphHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if(viewHolder is GraphHolder) {
            val holder : GraphHolder = viewHolder

            val item : Score = list[position]

            val entries: MutableList<BarEntry> = ArrayList()
            entries.clear()
            entries.add(BarEntry(0.0f, item.user.toFloat())) //User
            entries.add(BarEntry(2.0f, item.best.toFloat())) //Best
            entries.add(BarEntry(4.0f, item.average.toFloat())) //Average

            val max = item.best.toFloat()

            setGraph(holder.barChart, max, entries, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: MutableList<Score>) {
        Utils.print("Scores: ${list.size}")
        this.list = list
        notifyDataSetChanged()
    }

    private fun setGraph(barChart: BarChart, max: Float, entries: MutableList<BarEntry>, position: Int) {
        val barDataSet = BarDataSet(entries, "Examen " + (position + 1))
        val barData = BarData(barDataSet)

        barDataSet.setColors(
            Color.rgb(193, 37, 82),
            Color.rgb(255, 102, 0),
            Color.rgb(245, 199, 0)
        )

        barData.barWidth = 1.0f

        barChart.data = barData
        barChart.setFitBars(false)
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(false)
        barChart.setScaleEnabled(false)
        barChart.invalidate()

        val yAxis1: YAxis = barChart.axisLeft
        yAxis1.textSize = 15f // set the text size
        yAxis1.axisMinimum = 0f // start at zero
        yAxis1.axisMaximum = max // the axis maximum is 100
        yAxis1.textColor = Color.BLACK
        yAxis1.granularity = 1f // interval 1

        val yAxis2: YAxis = barChart.axisRight
        yAxis2.textSize = 15f // set the text size
        yAxis2.axisMinimum = 0f // start at zero
        yAxis2.axisMaximum = max // the axis maximum is 100
        yAxis2.textColor = Color.BLACK
        yAxis2.granularity = 1f // interval 1
    }
}