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

package com.jorzet.casmal.ui

import android.content.Intent
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.jorzet.casmal.R
import com.jorzet.casmal.base.BaseActivity
import com.jorzet.casmal.managers.FirebaseRequestManager
import com.jorzet.casmal.managers.ServiceManager
import com.jorzet.casmal.models.Account
import com.jorzet.casmal.models.FlashCard
import com.jorzet.casmal.models.Level
import com.jorzet.casmal.models.User
import com.jorzet.casmal.utils.Utils
import com.jorzet.casmal.utils.Utils.Companion.PROVIDER_FACEBOOK
import com.jorzet.casmal.utils.Utils.Companion.PROVIDER_GOOGLE
import com.jorzet.casmal.viewmodels.AccountsViewModel

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 12/08/19.
 */

class SplashActivity: BaseActivity() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var ivGoogleLogin: ImageView
    private lateinit var ivFacebookLogin: ImageView

    private lateinit var containerLogin: RelativeLayout

    private val permissions: ArrayList<String> = ArrayList()

    private var viewModel: AccountsViewModel? = null

    /**
     * Constants
     */
    companion object {
        private const val TAG = "SplashActivity"
        private const val RC_SIGN_IN = 9001
        private const val TIME_DELAY: Long = 2000
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        ivGoogleLogin = findViewById(R.id.ivGoogleLogin)
        ivFacebookLogin = findViewById(R.id.ivFacebookLogin)
        containerLogin = findViewById(R.id.containerLogin)
    }

    override fun prepareComponents() {
        viewModel = ViewModelProviders.of(this).get(AccountsViewModel::class.java)

        viewModel?.loginFacebook?.observe(this, Observer {
            if(it) {
                facebookSignIn()
            }
        })

        viewModel?.loginGoogle?.observe(this, Observer {
            if(it) {
                googleSignIn()
            }
        })

        //Facebook Permissions
        permissions.add("email")
        permissions.add("public_profile")

        callbackManager = CallbackManager.Factory.create()
        auth = FirebaseAuth.getInstance()

        val facebookCallback: FacebookCallback<LoginResult> = object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Utils.print(TAG, "facebook:onSuccess:$loginResult")
                firebaseAuthWithFacebook(loginResult.accessToken)
            }

            override fun onError(ex: FacebookException?) {
                Utils.print(TAG, "facebook:onError $ex")
            }

            override fun onCancel() {
                Utils.print(TAG, "facebook:onCancel")
            }
        }

        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback)

        // Configure Google Sign In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        ivFacebookLogin.setOnClickListener {
            viewModel?.loginWithFacebook()
        }

        ivGoogleLogin.setOnClickListener {
            viewModel?.loginWithGoogle()
        }
    }

    /**
     * This method creates ans intent to show [MainActivity]
     */
    private fun goMainActivity(currentUser: FirebaseUser?) {
        val intent = Intent(this, MainActivity::class.java)

        FirebaseRequestManager.getInstance(this).requestLevels(object: FirebaseRequestManager.OnGetLevelsListener {
            override fun onGetLevelsSuccess(levels: List<Level>) {
                ServiceManager.getInstance().levels = levels
            }

            override fun onGetLevelsFail(throwable: Throwable) {
                
            }
        })

        FirebaseRequestManager.getInstance(this).requestUser(currentUser!!.uid, object : FirebaseRequestManager.OnGetUserListener {
            override fun onGetUserLoaded(user: User?) {
                if(user != null) {
                    FirebaseRequestManager.getInstance(this@SplashActivity).requestFlashCards(object: FirebaseRequestManager.OnGetFlashCardListener {
                        override fun onGetFlashCardSuccess(flashCard: FlashCard) {

                        }

                        override fun onGetFlashCardsSuccess(flashCards: List<FlashCard>) {

                            val userFlashcards: ArrayList<FlashCard> = arrayListOf()

                            for (userFlashCard in user.flashCards) {
                                for (flashCard in flashCards) {
                                    if (flashCard.id == userFlashCard) {
                                        userFlashcards.add(flashCard)
                                    }
                                }
                            }

                            ServiceManager.getInstance().user = user
                            ServiceManager.getInstance().userFlashCards = userFlashcards
                            ServiceManager.getInstance().flashCards = flashCards
                            startActivity(intent)
                            finish()
                        }

                        override fun onFlashCardFail(throwable: Throwable) {
                            ServiceManager.getInstance().user = user
                            startActivity(intent)
                            finish()
                        }

                    })

                } else {
                    //TODO InsertNewEmptyModel
                    pushUser(currentUser)
                }
            }

            override fun onGetUserError(throwable: Throwable) {
                Utils.print("Error 1: " + throwable.message)
                //TODO Protocol to exception
                finish()
            }
        })
    }

    private fun pushUser(firebaseUser: FirebaseUser) {
        Utils.print("start PushUser: " + firebaseUser.uid)
        val intent = Intent(this, MainActivity::class.java)

        FirebaseRequestManager.getInstance(this).insertUser(firebaseUser.uid, object : FirebaseRequestManager.OnInsertUserListener {
            override fun onSuccessUserInserted() {
                startActivity(intent)
                finish()
            }

            override fun onErrorUserInserted(throwable: Throwable) {
                Utils.print("Error 2: " + throwable.message)
                //TODO Protocol to exception
                finish()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (ex: ApiException) {
                // Google Sign In failed, update UI appropriately
                Utils.print(TAG, "Google sign in failed $ex")

                Toast.makeText(this, R.string.text_error_login, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithFacebook(token: AccessToken) {
        Utils.print(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information

                loginSuccess(PROVIDER_FACEBOOK)
            } else {
                // If sign in fails, display a message to the user.
                Utils.print(TAG, "signInWithCredential:failure ${task.exception.toString()}")
                Toast.makeText(this, "Authentication failed ${task.exception.toString()}", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun firebaseAuthWithGoogle(googleSignInAccount: GoogleSignInAccount) {
        Utils.print(TAG, "firebaseAuthWithGoogle:" + googleSignInAccount.id!!)

        val credential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Utils.print(TAG, "signInWithCredentialGoogle:success")
                loginSuccess(PROVIDER_GOOGLE)
            } else {
                // If sign in fails, display a message to the user.
                Utils.print(TAG, "signInWithCredential:failure ${task.exception.toString()}")
                Toast.makeText(this, "Authentication failed ${task.exception.toString()}", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun loginSuccess(provider: String) {
        // Sign in success, update UI with the signed-in user's information
        Utils.print(TAG, "signInWithCredential:success")
        val user = auth.currentUser

        val account = Account(
            1,
            user?.uid.toString(),
            user?.displayName.toString(),
            user?.email.toString(),
            user?.photoUrl.toString(), provider)
        viewModel?.insert(account)

        updateUI(user)
    }

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser

        Handler().postDelayed({
            updateUI(currentUser)
        }, TIME_DELAY)
    }

    private fun facebookSignIn() {
        LoginManager.getInstance().logInWithReadPermissions(this, permissions)
    }

    private fun googleSignIn() {
        startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        // Firebase sign out
        auth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            updateUI(null)
        }

        // Facebook sign out
        LoginManager.getInstance().logOut()

        Utils.print("signOut()")
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null) {
            Utils.print("User not is null: $currentUser")

            ivFacebookLogin.visibility = View.GONE
            ivGoogleLogin.visibility = View.GONE

            Utils.print(currentUser.uid)

            goMainActivity(currentUser)

            Handler().postDelayed({
                //TODO Test login
                //signOut()
            }, TIME_DELAY)
        } else {
            Utils.print("User is null")

            ivFacebookLogin.visibility = View.VISIBLE
            ivGoogleLogin.visibility = View.VISIBLE
        }
    }
}