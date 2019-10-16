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

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.jorzet.casmal.R
import com.jorzet.casmal.models.Subject
import com.jorzet.casmal.models.SubjectType
import kotlinx.android.synthetic.main.custom_subject_item.view.*

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 16/08/19.
 */

/**
 * Constants
 */
private const val TAG: String = "SubjectsAdapter"

class SubjectsAdapter(context: Context, subjects: List<Subject>): BaseAdapter() {

    /**
     * Attributes
     */
    private val mContext: Context = context
    lateinit var mSubjectClickListener: OnSubjectClickListener

    /**
     * Model
     */
    private val mSubjectList = subjects

    interface OnSubjectClickListener {
        fun onSubjectClick(subject: Subject)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view: View? = null

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(mContext)
            view = layoutInflater.inflate(R.layout.custom_subject_item, parent, false)

            if (position % 2 == 0) {
                val subject = getItem(position / 2)

                view.background = ContextCompat.getDrawable(mContext, R.drawable.subject_background)

                when (subject.subjectType) {
                    SubjectType.NEUROLOGY -> {
                        view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_neurology_white)
                    }
                    SubjectType.BIOCHEMISTRY -> {
                        view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_biochemistry_white)
                    }
                    SubjectType.EPIDEMIOLOGY -> {
                        view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_epidemiology_white)
                    }
                    else -> {
                        Log.d(TAG, "unknown subjects")
                    }
                }

                view.setOnClickListener {
                    if (::mSubjectClickListener.isInitialized) {
                        mSubjectClickListener.onSubjectClick(subject)
                    }
                }
            }
        } else {
            view = convertView;
        }

        return view
    }

    override fun getItem(position: Int): Subject {
        return mSubjectList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return mSubjectList.size + mSubjectList.size - 1
    }

}