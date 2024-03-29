/*
 * Copyright [2019] [Jorge Zepeda Tinoco]
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
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.jorzet.casmal.R
import com.jorzet.casmal.models.Module
import kotlinx.android.synthetic.main.custom_module_item.view.*

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 20/08/19.
 */

class ModulesAdapter(modules: List<Module>): BaseAdapter() {

    /**
     * Model
     */
    private val mModules = modules

    /**
     * Attributes
     */
    lateinit var mModuleClickListener: OnModuleClickListener

    val moduleImage = intArrayOf(
        R.drawable.ic_module_1,
        R.drawable.ic_module_2,
        R.drawable.ic_module_3,
        R.drawable.ic_module_4,
        R.drawable.ic_module_5,
        R.drawable.ic_module_6,
        R.drawable.ic_module_7,
        R.drawable.ic_module_8,
        R.drawable.ic_module_9)

    interface OnModuleClickListener {
        fun onModuleClick(module: Module)
    }



    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(parent?.context)
            view = layoutInflater.inflate(R.layout.custom_module_item, parent, false)
        } else {
            view = convertView
        }

        val module = getItem(position)

        view.iv_module_image.background = ContextCompat.getDrawable(parent?.context!!, moduleImage[position])

        view.tv_module.text = module.moduleName

        view.setOnClickListener {
            if (::mModuleClickListener.isInitialized) {
                mModuleClickListener.onModuleClick(module)
            }
        }

        return view
    }

    override fun getItem(position: Int): Module {
        return mModules[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return mModules.size
    }
}