package com.jorzet.casmal.fragments.question

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jorzet.casmal.R
import com.jorzet.casmal.fragments.BaseFragment
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.models.Question

class MultipleQuestionFragment: BaseFragment() {

    private lateinit var mText: TextView
    private lateinit var mOptionA: TextView
    private lateinit var mOptionB: TextView
    private lateinit var mOptionC: TextView
    private lateinit var mOptionD: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (container == null)
            return null

        val rootView = inflater.inflate(R.layout.multiple_question_fragment, container, false)!!

        FirebaseRequestManager.getInstance(context!!).requestQuestion("q1", object: FirebaseRequestManager.OnGetQuestionListener {
            override fun onGetQuestionLoaded(question: Question) {
                Log.d("","")
            }
            override fun onGetQuestionError(throwable: Throwable) {
                Log.d("","")
            }
        })

        return rootView;
    }

}