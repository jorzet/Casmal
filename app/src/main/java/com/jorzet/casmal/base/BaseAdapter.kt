package com.jorzet.casmal.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.jorzet.casmal.interfaces.ItemListener
import com.jorzet.casmal.viewholders.ViewHolder

abstract class BaseAdapter<Model>(
    protected val context: Context,
    protected val list: MutableList<Model>? = null,
    protected val listener: ItemListener<Model>

): RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        if(list != null) {
            return list.size
        }

        return 0
    }

    open fun setList(list: MutableList<Model>) {
        this.list?.clear()
        this.list?.addAll(list)
        notifyDataSetChanged()
    }
}