package com.jorzet.casmal.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.jorzet.casmal.interfaces.ItemListener
import com.jorzet.casmal.viewholders.ViewHolder

abstract class BaseAdapter<Model>(
    protected val context: Context,
    protected val list: ArrayList<Model>,
    protected val listener: ItemListener<Model>

): RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }
}