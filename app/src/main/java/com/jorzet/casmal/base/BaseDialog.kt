package com.jorzet.casmal.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.jorzet.casmal.R

abstract class BaseDialog: DialogFragment() {

    protected lateinit var rootView: View


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(activity)
        val rootView = onCreateView(inflater, null, savedInstanceState)

        val dialog = Dialog(context!!, R.style.AppDialog)
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

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun prepareComponents()
}