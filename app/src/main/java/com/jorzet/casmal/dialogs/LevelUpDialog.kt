package com.jorzet.casmal.dialogs

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseDialog

class LevelUpDialog: BaseDialog() {

    /**
     * UI accessors
     */
    private lateinit var mOkButton: View

    private var onOkButtonListener: OnOkButtonListener? = null

    interface OnOkButtonListener {
        fun onOkButtonClick()
    }

    companion object {
        private const val ARG_LEVEL: String = "arg_level"
        private const val ARG_FLASH_CARD: String = "arg_flash_card"
        private const val ARG_IS_LISTENER_ACTIVITY : String = "arg_is_listener_activity"

        fun newInstance(level: Int, flashCard: String, onOkButtonListener: OnOkButtonListener) : LevelUpDialog {
            val levelUpDialog = LevelUpDialog()

            val args = Bundle()

            args.putInt(ARG_LEVEL, level)
            args.putString(ARG_FLASH_CARD, flashCard)

            if (onOkButtonListener is Activity) {
                args.putBoolean(ARG_IS_LISTENER_ACTIVITY, true)
            } else {
                args.putBoolean(ARG_IS_LISTENER_ACTIVITY, false)
                levelUpDialog.setTargetFragment(onOkButtonListener as Fragment, 0)
            }

            levelUpDialog.arguments = args

            return levelUpDialog
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_level_up
    }

    override fun initView() {
        mOkButton = rootView.findViewById(R.id.btn_ok)
    }

    override fun prepareComponents() {



        mOkButton.setOnClickListener {
            dismiss()
            onOkButtonListener?.onOkButtonClick()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (arguments!!.getBoolean(ARG_IS_LISTENER_ACTIVITY)) {
            onOkButtonListener = activity as OnOkButtonListener;
        } else {
            onOkButtonListener = targetFragment as OnOkButtonListener
        }
    }

    override fun onDetach() {
        super.onDetach()
        onOkButtonListener = null
    }


}