/*
 * Copyright [2020] [Bani Azarael Mejia Flores]
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

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jorzet.casmal.R
import com.jorzet.casmal.adapters.FlashCardAdapter
import com.jorzet.casmal.base.BaseFragment
import com.jorzet.casmal.dialogs.FullScreeImageDialog
import com.jorzet.casmal.interfaces.ItemListener
import com.jorzet.casmal.managers.ImageManager
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.ui.PaywayActivity
import com.jorzet.casmal.utils.Utils
import com.jorzet.casmal.utils.Utils.Companion.PROVIDER_FACEBOOK
import com.jorzet.casmal.utils.Utils.Companion.PROVIDER_GOOGLE
import com.jorzet.casmal.viewmodels.UserViewModel

/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 12/08/19.
 */

class ProfileFragment: BaseFragment() {
    private lateinit var viewModel: UserViewModel
    private lateinit var tvUserName: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var ivPhoto: ImageView
    private lateinit var ivFacebookCircle: ImageView
    private lateinit var ivGoogleCircle: ImageView
    private lateinit var ivEmailCircle: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var noFlashcards: TextView
    private lateinit var paywayButton: Button

    private lateinit var adapter: FlashCardAdapter

    override fun getLayoutId(): Int {
        return R.layout.profile_fragment
    }

    override fun initView() {
        tvUserName = rootView.findViewById(R.id.tvUserName)
        tvUserEmail = rootView.findViewById(R.id.tvUserEmail)
        ivPhoto = rootView.findViewById(R.id.ivPhoto)
        ivFacebookCircle = rootView.findViewById(R.id.ivFacebookCircle)
        ivGoogleCircle = rootView.findViewById(R.id.ivGoogleCircle)
        ivEmailCircle = rootView.findViewById(R.id.ivEmailCircle)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        noFlashcards = rootView.findViewById(R.id.tv_no_flashcards)
        paywayButton = rootView.findViewById(R.id.btn_payway)
    }

    override fun prepareComponents() {
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        adapter = FlashCardAdapter(object : ItemListener<FlashCard> {
            override fun onItemSelected(model: FlashCard) {
                Utils.print("ItemId: ${model.id}")

                // show full screen image
                FullScreeImageDialog
                    .newInstance(model.storageName)
                    .show(fragmentManager!!,"full_screen_image")
            }
        })

        viewModel.getAccount().observe(this, Observer {
            if(it != null) {
                Utils.print("Accounts Update size = {${it.userName}}")

                tvUserName.text = it.userName
                tvUserEmail.text = it.userEmail

                var urlPhoto: String = it.image

                when (it.provider) {
                    PROVIDER_FACEBOOK -> {
                        urlPhoto = "$urlPhoto?type=large"

                        ivFacebookCircle.visibility = View.VISIBLE
                        ivGoogleCircle.visibility = View.GONE
                        ivEmailCircle.visibility = View.GONE
                    }
                    PROVIDER_GOOGLE -> {
                        ivFacebookCircle.visibility = View.GONE
                        ivGoogleCircle.visibility = View.VISIBLE
                        ivEmailCircle.visibility = View.GONE
                    }
                    else -> {
                        ivFacebookCircle.visibility = View.GONE
                        ivGoogleCircle.visibility = View.GONE
                        ivEmailCircle.visibility = View.GONE
                    }
                }

                ImageManager.instance.setImage(urlPhoto, ivPhoto)
            }
        })

        viewModel.getFlashCards().observe(this, Observer {
            if(it.isEmpty()) {
                recyclerView.visibility = View.GONE
                noFlashcards.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                noFlashcards.visibility = View.GONE
            }

            adapter.setList(it)
        })

        //if (userFlashCards.size > 3) {
            //list.add(FlashCard("0", "LoadModel"))
        //}

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        paywayButton.setOnClickListener(paywayButtonClickListener)

        viewModel.updateFlashCards()
    }

    private val paywayButtonClickListener = View.OnClickListener {
        val intent = Intent(activity, PaywayActivity::class.java)
        startActivity(intent)
    }
}