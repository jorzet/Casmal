package com.jorzet.casmal.viewholders

import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.jorzet.casmal.R

class GraphHolder(itemView: View) : ViewHolder(itemView) {
    val barChart: BarChart = itemView.findViewById(R.id.barChart)
    val barChartTitle: TextView = itemView.findViewById(R.id.tv_graph_title)
}