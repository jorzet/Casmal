package com.jorzet.casmal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseAdapter
import com.jorzet.casmal.interfaces.ItemListener
import com.jorzet.casmal.managers.FirebaseStorageManager
import com.jorzet.casmal.managers.ImageManager
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.viewholders.FlashCardHolder
import com.jorzet.casmal.viewholders.ViewHolder

class FlashCardAdapter(
    list: ArrayList<FlashCard>, listener: ItemListener<FlashCard>
) : BaseAdapter<FlashCard>(list, listener) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_flash_card, parent, false)
        return FlashCardHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if(viewHolder is FlashCardHolder) {
            val item: FlashCard = list[position]

            val holder: FlashCardHolder = viewHolder

            val image: String = FirebaseStorageManager.getImage(item.storageName)

            ImageManager.getInstance().setImage(image, holder.ivFlashCard)
        }
    }
}