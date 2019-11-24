/*
 * Copyright [2019] [Bani Azarael Mejia Flores]
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

package com.jorzet.casmal.fragments

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseFragment
import com.jorzet.casmal.managers.ImageManager
import com.jorzet.casmal.utils.Utils
import com.jorzet.casmal.utils.Utils.Companion.PROVIDER_FACEBOOK
import com.jorzet.casmal.utils.Utils.Companion.PROVIDER_GOOGLE
import com.jorzet.casmal.viewmodels.AccountsViewModel

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 12/08/19.
 */

class ProfileFragment: BaseFragment() {
    private var viewModel: AccountsViewModel? = null
    private var tvUserName: TextView? = null
    private var tvUserEmail: TextView? = null
    private var ivPhoto: ImageView? = null
    private var ivFacebookCircle: ImageView? = null
    private var ivGoogleCircle: ImageView? = null
    private var ivEmailCircle: ImageView? = null

    companion object {
        private const val TAG = "ProfileFragment"
    }

    override fun getFragmentActivity(): FragmentActivity {
        return this.activity!!
    }

    @LayoutRes
    override fun getLayoutId(): Int {
        return R.layout.profile_fragment
    }

    override fun initView() {
        tvUserName = rootView.findViewById(R.id.tvUserName)
        tvUserEmail = rootView.findViewById(R.id.tvUserEmail)
        ivPhoto = rootView.findViewById(R.id.ivPhoto)
        ivFacebookCircle = rootView.findViewById(R.id.ivFacebookCircle)
        ivEmailCircle = rootView.findViewById(R.id.ivEmailCircle)
    }

    override fun prepareComponents() {
        viewModel = ViewModelProviders.of(this).get(AccountsViewModel::class.java)

        viewModel?.list?.observe(this, Observer { list ->
            list.let {
                Utils.print("Accounts Update size = {${it[0].userName}}")

                tvUserName?.text = it[0].userName
                tvUserEmail?.text = it[0].userEmail

                var urlPhoto: String = it[0].image

                when (it[0].provider) {
                    PROVIDER_FACEBOOK -> {
                        urlPhoto = "$urlPhoto?type=large"

                        ivFacebookCircle?.visibility = View.VISIBLE
                        ivGoogleCircle?.visibility = View.GONE
                        ivEmailCircle?.visibility = View.GONE
                    }
                    PROVIDER_GOOGLE -> {
                        ivFacebookCircle?.visibility = View.GONE
                        ivGoogleCircle?.visibility = View.VISIBLE
                        ivEmailCircle?.visibility = View.GONE
                    }
                    else -> {
                        ivFacebookCircle?.visibility = View.GONE
                        ivGoogleCircle?.visibility = View.GONE
                        ivEmailCircle?.visibility = View.GONE
                        //TODO Email Alpha
                    }
                }

                ImageManager.getInstance().setImage(urlPhoto, ivPhoto)
            }
        })
    }
}