package com.jorzet.casmal.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseAdapter
import com.jorzet.casmal.interfaces.ItemListener
import com.jorzet.casmal.managers.FirebaseStorageManager
import com.jorzet.casmal.managers.ImageManager
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.viewholders.FlashCardHolder
import com.jorzet.casmal.viewholders.LoadMoreHolder
import com.jorzet.casmal.viewholders.ViewHolder

class FlashCardAdapter(
    context: Context,
    list: ArrayList<FlashCard>,
    listener: ItemListener<FlashCard>
) : BaseAdapter<FlashCard>(context, list, listener) {

    companion object {
        const val ITEM_CARD = 0
        const val ITEM_LOAD = 1
    }

    override fun getItemViewType(position: Int): Int {
        if(list[position].id == "0") {
            return ITEM_LOAD
        }

        return ITEM_CARD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_CARD -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flash_card, parent, false)
                FlashCardHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_load_more, parent, false)
                LoadMoreHolder(view)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item: FlashCard = list[position]

        if(viewHolder is FlashCardHolder) {
            val holder: FlashCardHolder = viewHolder

            holder.ivFlashCard.setOnClickListener {
                listener.onItemSelected(item)
            }

            val storageReference = FirebaseStorageManager.getImage(item.storageName)

            ImageManager.getInstance().setImage(storageReference, holder.ivFlashCard)
        } else if(viewHolder is LoadMoreHolder) {
            val holder: LoadMoreHolder = viewHolder

            holder.btnLoadMore.setOnClickListener {
                listener.onItemSelected(item)
            }
        }
    }
}