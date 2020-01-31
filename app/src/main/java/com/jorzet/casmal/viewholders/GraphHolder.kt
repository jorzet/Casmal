package com.jorzet.casmal.viewholders

import android.view.View
import com.github.mikephil.charting.charts.BarChart
import com.jorzet.casmal.R

class GraphHolder(itemView: View) : ViewHolder(itemView) {
    val barChart: BarChart = itemView.findViewById(R.id.barChart)
}