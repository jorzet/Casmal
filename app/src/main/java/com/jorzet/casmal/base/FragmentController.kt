package com.jorzet.casmal.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

abstract class FragmentController: Fragment() {
    protected lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(getLayoutId(), container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        prepareComponents()
    }

    abstract fun getLayoutId(): Int
    abstract fun getFragmentActivity(): FragmentActivity
    abstract fun initView()
    abstract fun prepareComponents()
}