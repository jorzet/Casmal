package com.jorzet.casmal.dialogs

import android.view.View
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseDialog

class AlreadyPremiumDialog: BaseDialog() {

    /**
     * UI accessors
     */
    private lateinit var mOkButton: View

    companion object {
        private const val ARG_IS_LISTENER_ACTIVITY: String = "arg_is_listener_activity"

        fun newInstance(): AlreadyPremiumDialog {
            return AlreadyPremiumDialog()
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_already_premium
    }

    override fun initView() {
        mOkButton = rootView.findViewById(R.id.btn_ok)
    }

    override fun prepareComponents() {
        mOkButton.setOnClickListener {
            dismiss()
        }
    }

}