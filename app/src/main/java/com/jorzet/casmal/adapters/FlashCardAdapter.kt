package com.jorzet.casmal.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseAdapter
import com.jorzet.casmal.interfaces.ItemListener
import com.jorzet.casmal.managers.FirebaseStorageManager
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.viewholders.FlashCardHolder
import com.jorzet.casmal.viewholders.ViewHolder


class FlashCardAdapter(
    context: Context,
    list: ArrayList<FlashCard>,
    listener: ItemListener<FlashCard>
) : BaseAdapter<FlashCard>(context, list, listener) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_flash_card, parent, false)
        return FlashCardHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if(viewHolder is FlashCardHolder) {
            val item: FlashCard = list[position]

            val holder: FlashCardHolder = viewHolder

            holder.ivFlashCard.setOnClickListener {
                listener.onItemSelected(item)
            }

            val storageReference = FirebaseStorageManager.getInstance().reference.child(item.storageName)

            Glide.with(context).load(storageReference).into(holder.ivFlashCard)
        }
    }
}