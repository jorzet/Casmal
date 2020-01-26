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

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.jorzet.casmal.R

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 10/01/20.
 */

abstract class BaseDialog: DialogFragment() {
    /**
     * UI accessors
     */
    protected lateinit var rootView: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(activity)
        val rootView = onCreateView(inflater, null, savedInstanceState)

        val dialog = Dialog(context!!, getDialogStyle())
        dialog.setContentView(rootView!!)

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(getLayoutId(), container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        prepareComponents()
    }

    /**
     * This method returns custom [Dialog] layout resource
     * @return dialog layout resource
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * This method is to init each [View] component with rootView attribute
     */
    abstract fun initView()

    /**
     * This method is just to set data, listeners or handle [View] components
     */
    abstract fun prepareComponents()

    /**
     * This method returns custom dialog style
     * @return dialog style resource
     */
    open fun getDialogStyle(): Int {
        return R.style.AppDialog
    }
}