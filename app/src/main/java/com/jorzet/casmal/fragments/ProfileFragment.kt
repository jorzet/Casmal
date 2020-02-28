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
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.jorzet.casmal.BuildConfig
import com.jorzet.casmal.R
import com.jorzet.casmal.adapters.FlashCardAdapter
import com.jorzet.casmal.base.BaseActivity
import com.jorzet.casmal.base.BaseFragment
import com.jorzet.casmal.dialogs.FullScreeImageDialog
import com.jorzet.casmal.interfaces.ItemListener
import com.jorzet.casmal.managers.ImageManager
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.ui.MainActivity
import com.jorzet.casmal.ui.PaywayActivity
import com.jorzet.casmal.utils.Utils
import com.jorzet.casmal.utils.Utils.Companion.PROVIDER_FACEBOOK
import com.jorzet.casmal.utils.Utils.Companion.PROVIDER_GOOGLE
import com.jorzet.casmal.viewmodels.UserViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author Bani Azarael Mejia Flores
 * @email banimejia@codequark.com
 * @date 12/08/19.
 */

class ProfileFragment: BaseFragment() {
    private lateinit var tvUserName: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var ivPhoto: ImageView
    private lateinit var ivFacebookCircle: ImageView
    private lateinit var ivGoogleCircle: ImageView
    private lateinit var ivEmailCircle: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var noFlashcards: TextView
    private lateinit var paywayButton: Button
    private lateinit var suggestionButton: Button

    private lateinit var adapter: FlashCardAdapter

    private lateinit var viewModel: UserViewModel

    override fun getLayoutId(): Int {
        return R.layout.profile_fragment
    }

    override fun initView() {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        tvUserName = rootView.findViewById(R.id.tvUserName)
        tvUserEmail = rootView.findViewById(R.id.tvUserEmail)
        ivPhoto = rootView.findViewById(R.id.ivPhoto)
        ivFacebookCircle = rootView.findViewById(R.id.ivFacebookCircle)
        ivGoogleCircle = rootView.findViewById(R.id.ivGoogleCircle)
        ivEmailCircle = rootView.findViewById(R.id.ivEmailCircle)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        noFlashcards = rootView.findViewById(R.id.tv_no_flashcards)
        paywayButton = rootView.findViewById(R.id.btn_payway)
        suggestionButton = rootView.findViewById(R.id.btn_suggest)
    }

    override fun prepareComponents() {
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

        checkIsPremium()

        paywayButton.setOnClickListener(paywayButtonClickListener)
        suggestionButton.setOnClickListener {
            goSendEmailActivity()
        }

        viewModel.updateFlashCards()
    }

    override fun onResume() {
        super.onResume()

        checkIsPremium()
    }

    private val paywayButtonClickListener = View.OnClickListener {
        val intent = Intent(activity, PaywayActivity::class.java)
        startActivityForResult(intent, MainActivity.PREMIUM_RESULT_CODE)
    }

    /*
     * This method open the native mail app to send an email to soporte@zerebrez.com
     */
    private fun goSendEmailActivity() {
        //val intent = Intent(activity, SendEmailActivity::class.java)
        //activity!!.startActivity(intent)
        val emailIntent = Intent(Intent.ACTION_SENDTO,
            Uri.fromParts("mailto", resources.getString(R.string.support_email_text), null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
        val userFirebase = FirebaseAuth.getInstance().currentUser
        var userUUID = ""
        var userEmail = ""
        val versionName = BuildConfig.VERSION_NAME
        if (userFirebase != null) {
            userUUID = userFirebase.uid
            if (userFirebase.email != null && !userFirebase.email.equals("")) {
                userEmail = userFirebase.email!!
            }
        }
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Sistema Operativo: " + (activity as BaseActivity).getAndroidVersion() +
                "\n\n\n Versión app: " + versionName +
                "\n Cuenta: " + userUUID +
                "\n Correo: " + userEmail +
                "\n\n\n Aquí escribe tu mensaje" + "" +
                "\n\n\n (Para un mejor soporte no borres el sistema operativo ni la cuenta)")
        startActivity(Intent.createChooser(emailIntent, "Enviando email..."))
    }

    /**
     * Validate view according isPremium flag
     */
    private fun checkIsPremium() {
        val isPremium = viewModel.getUser().value?.payment?.isPremium!!

        paywayButton.isEnabled = !isPremium

        if (isPremium) {
            val renewSubscription = viewModel.getUser().value?.payment?.timeStamp!! + 2592000000

            val simple: DateFormat = SimpleDateFormat("dd MMM yyyy")

            val result = Date(renewSubscription)
            val output = simple.format(result)
            paywayButton.text = resources.getString(R.string.buy_membership_in_time) + " " + output
        }
    }
}