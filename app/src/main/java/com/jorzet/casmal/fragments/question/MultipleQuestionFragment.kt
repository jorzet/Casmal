package com.jorzet.casmal.fragments.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jorzet.casmal.R
import com.jorzet.casmal.fragments.BaseFragment

class MultipleQuestionFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (container == null)
            return null

        val rootView = inflater.inflate(R.layout.multiple_question_fragment, container, false)!!

        return rootView;
    }

}