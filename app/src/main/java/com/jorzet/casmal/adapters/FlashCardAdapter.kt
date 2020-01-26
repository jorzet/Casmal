/*
 * Copyright [2020] [Jorge Zepeda Tinoco]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jorzet.casmal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jorzet.casmal.R
import com.jorzet.casmal.interfaces.ItemListener
import com.jorzet.casmal.managers.FirebaseStorageManager
import com.jorzet.casmal.managers.ImageManager
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.utils.Utils
import com.jorzet.casmal.viewholders.FlashCardHolder
import com.jorzet.casmal.viewholders.LoadMoreHolder
import com.jorzet.casmal.viewholders.ViewHolder

class FlashCardAdapter(
    private val listener: ItemListener<FlashCard>
) : RecyclerView.Adapter<ViewHolder>() {
    private var list: MutableList<FlashCard> = ArrayList()

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

            ImageManager.instance.setImage(storageReference, holder.ivFlashCard)
        } else if(viewHolder is LoadMoreHolder) {
            val holder: LoadMoreHolder = viewHolder

            holder.btnLoadMore.setOnClickListener {
                listener.onItemSelected(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: MutableList<FlashCard>) {
        Utils.print("Set new FlashCardAdapter list: ${list.size}")
        this.list = list
        notifyDataSetChanged()
    }
}