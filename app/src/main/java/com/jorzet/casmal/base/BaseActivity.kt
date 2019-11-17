package com.jorzet.casmal.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

abstract class BaseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        initView()
        prepareComponents()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun getActivity(): FragmentActivity
    abstract fun initView()
    abstract fun prepareComponents()
}