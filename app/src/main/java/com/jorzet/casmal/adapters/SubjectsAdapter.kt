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
import androidx.recyclerview.widget.RecyclerView
import com.jorzet.casmal.R
import com.jorzet.casmal.models.Subject
import com.jorzet.casmal.models.SubjectType
import kotlinx.android.synthetic.main.custom_subject_item.view.*

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 16/08/19.
 */

class SubjectsAdapter(context: Context, subjects: List<Subject>): RecyclerView.Adapter<SubjectsAdapter.SubjectViewHolder>() {

    companion object {
        /**
         * Constants
         */
        private const val TAG: String = "SubjectsAdapter"
    }

    /**
     * Attributes
     */
    private val mContext: Context = context
    lateinit var mSubjectClickListener: OnSubjectClickListener

    /**
     * Model
     */
    private val mSubjectList = subjects

    /** Interface to catch item click listener **/
    interface OnSubjectClickListener {
        /**
         * This method is called when item view is clicked
         */
        fun onSubjectClick(subject: Subject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.custom_subject_item, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        if (position % 2 == 0) {
            val subject = getItem(position / 2)

            holder.view.background = ContextCompat.getDrawable(mContext, R.drawable.subject_background)

            when (subject.internalName) {
                SubjectType.NEUROLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_neurology_white)
                SubjectType.BIOCHEMISTRY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_biochemistry_white)
                SubjectType.EPIDEMIOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_epidemiology_white)
                SubjectType.ANATOMY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_anatomy_white)
                SubjectType.CARDIOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_cardiology_white)
                SubjectType.CLINIC ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_clinic_white)
                SubjectType.DERMATOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_dermatology_white)
                SubjectType.EMBRYOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_embriology_white)
                SubjectType.ENDOCRINOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_endocrinology_white)
                SubjectType.GASTROENTEROLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_gastroenterology_white)
                SubjectType.GASTROLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_gastronomy_white)
                SubjectType.GENETICS ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_genetics_white)
                SubjectType.GYNECOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_ginecology_white)
                SubjectType.HEMATOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_hematology_white)
                SubjectType.HISTOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_histology_white)
                SubjectType.IMMUNOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_inmunology_white)
                SubjectType.INFECTOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_infectology_white)
                SubjectType.MICROBIOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_microbiology_white)
                SubjectType.NEPHROLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_nephrology_white)
                SubjectType.ONCOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_oncology_white)
                SubjectType.OPHTHALMOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_ophthalmology_white)
                SubjectType.OTORHINOLARYNGOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_otorhinolaryngology_white)
                SubjectType.PATHOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_patology_white)
                SubjectType.PEDIATRICS ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_pediatrics_white)
                SubjectType.PHARMACOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_pharmacology_white)
                SubjectType.PHYSIOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_physiology_white)
                SubjectType.PNEUMOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_pneumology_white)
                SubjectType.PNEUMOLOGY_DERMATOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_dermatology_white)
                SubjectType.PSYCHIATRY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_psychiatry_white)
                SubjectType.SURGERY_ANESTHESIOLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_surgery_anesthesiology_white)
                SubjectType.TRAUMATOLOGY_ORTHOPEDICS ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_traumatology_orthopedics_white)
                SubjectType.UROLOGY ->
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.ic_urology_white)
                else -> {
                    holder.view.image.background = ContextCompat.getDrawable(mContext, R.drawable.no_subject_background)
                    Log.d(TAG, "unknown subjects")
                }
            }

            holder.view.setOnClickListener {
                if (::mSubjectClickListener.isInitialized) {
                    mSubjectClickListener.onSubjectClick(subject)
                }
            }
        } else {
            holder.view.background = ContextCompat.getDrawable(mContext, R.drawable.no_subject_background)
        }
    }

    override fun getItemCount(): Int {
        return mSubjectList.size + mSubjectList.size - 1
    }

    /**
     *
     */
    private fun getItem(position: Int): Subject {
        return mSubjectList[position]
    }

    /**
     *
     */
    class SubjectViewHolder(v: View): RecyclerView.ViewHolder(v) {
        var view: View = v
    }
}