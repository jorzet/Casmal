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

package com.jorzet.casmal.fragments

import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.jorzet.casmal.R
import com.jorzet.casmal.base.FragmentController

/**
 * @author Jorge Zepeda Tinoco
 * @email jorzet.94@gmail.com
 * @date 12/08/19.
 */

class ProfileFragment: FragmentController() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val permissions: ArrayList<String> = ArrayList()

    private lateinit var ivGoogleLogin: ImageView
    private lateinit var ivFacebookLogin: ImageView

    companion object {
        private const val TAG = "ProfileFragment"
        private const val RC_SIGN_IN = 9001
    }

    override fun getFragmentActivity(): FragmentActivity {
        return this.activity!!
    }

    override fun initView() {
        ivGoogleLogin = rootView.findViewById(R.id.ivGoogleLogin)
        ivFacebookLogin = rootView.findViewById(R.id.ivFacebookLogin)
    }

    override fun prepareComponents() {
        //Facebook Permissions
        permissions.add("email")
        permissions.add("public_profile")

        callbackManager = CallbackManager.Factory.create()
        firebaseAuth = FirebaseAuth.getInstance()

        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                firebaseAuthWithFacebook(loginResult.accessToken)
            }

            override fun onError(ex: FacebookException?) {
                Log.d(TAG, "facebook:onError", ex)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }
        })

        // Configure Google Sign In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(getFragmentActivity(), googleSignInOptions)

        ivFacebookLogin.setOnClickListener {
            facebookSignIn()
        }

        ivGoogleLogin.setOnClickListener {
            googleSignIn()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.profile_fragment
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
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithFacebook(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(getFragmentActivity()) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val user = firebaseAuth.currentUser
                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.d(TAG, "signInWithCredential:failure", task.exception)
                Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun firebaseAuthWithGoogle(googleSignInAccount: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + googleSignInAccount.id!!)

        val credential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(getFragmentActivity()) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val user = firebaseAuth.currentUser
                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                Snackbar.make(rootView, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.currentUser
        updateUI(currentUser)
    }

    private fun facebookSignIn() {
        LoginManager.getInstance().logInWithReadPermissions(getFragmentActivity(), permissions)
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        // Firebase sign out
        firebaseAuth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(getFragmentActivity()) {
            updateUI(null)
        }
    }

    // Firebase sign out and Google revoke
    private fun revokeAccess() {
        firebaseAuth.signOut()

        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(getFragmentActivity()) {
            updateUI(null)
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {

    }
}