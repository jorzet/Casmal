package com.jorzet.casmal.base

import androidx.recyclerview.widget.RecyclerView
import com.jorzet.casmal.interfaces.ItemListener
import com.jorzet.casmal.viewholders.ViewHolder

abstract class BaseAdapter<L>(
    protected val list: ArrayList<L>,
    protected val listener: ItemListener<L>

): RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }
}