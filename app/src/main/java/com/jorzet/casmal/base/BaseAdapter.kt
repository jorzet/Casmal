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