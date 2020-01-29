package com.jorzet.casmal.dialogs

import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseDialog

class BecomePremiumDialog: BaseDialog() {

    interface BecomePremiumListener {
        fun onBePremiumClickListener()
        fun onCancelClickListener()
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_become_premium
    }

    override fun initView() {

    }

    override fun prepareComponents() {

    }
}