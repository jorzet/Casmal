package com.jorzet.casmal.ui

import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
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
import com.jorzet.casmal.base.BaseActivity
import com.jorzet.casmal.utils.Utils

class LoginActivity: BaseActivity() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var ivGoogleLogin: ImageView
    private lateinit var ivFacebookLogin: ImageView

    private lateinit var containerLogin: LinearLayout

    private val permissions: ArrayList<String> = ArrayList()

    companion object {
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getActivity(): FragmentActivity {
        return this@LoginActivity
    }

    override fun initView() {
        ivGoogleLogin = findViewById(R.id.ivGoogleLogin)
        ivFacebookLogin = findViewById(R.id.ivFacebookLogin)
        containerLogin = findViewById(R.id.containerLogin)
    }

    override fun prepareComponents() {
        //Facebook Permissions
        permissions.add("email")
        permissions.add("public_profile")

        callbackManager = CallbackManager.Factory.create()
        firebaseAuth = FirebaseAuth.getInstance()

        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Utils.printDebug(TAG, "facebook:onSuccess:$loginResult")
                firebaseAuthWithFacebook(loginResult.accessToken)
            }

            override fun onError(ex: FacebookException?) {
                Utils.printDebug(TAG, "facebook:onError $ex")
            }

            override fun onCancel() {
                Utils.printDebug(TAG, "facebook:onCancel")
            }
        })

        // Configure Google Sign In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions)

        ivFacebookLogin.setOnClickListener {
            facebookSignIn()
        }

        ivGoogleLogin.setOnClickListener {
            googleSignIn()
        }
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
                Utils.printDebug(TAG, "Google sign in failed $ex")
                // ...
            }
        }
    }

    private fun firebaseAuthWithFacebook(token: AccessToken) {
        Utils.printDebug(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(getActivity()) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Utils.printDebug(TAG, "signInWithCredential:success")
                val user = firebaseAuth.currentUser
                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Utils.printDebug(TAG, "signInWithCredential:failure $task.exception")
                Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun firebaseAuthWithGoogle(googleSignInAccount: GoogleSignInAccount) {
        Utils.printDebug(TAG, "firebaseAuthWithGoogle:" + googleSignInAccount.id!!)

        val credential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(getActivity()) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Utils.printDebug(TAG, "signInWithCredential:success")
                val user = firebaseAuth.currentUser
                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Utils.printDebug(TAG, "signInWithCredential:failure $task.exception")
                Snackbar.make(containerLogin, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
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
        LoginManager.getInstance().logInWithReadPermissions(getActivity(), permissions)
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        // Firebase sign out
        firebaseAuth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(getActivity()) {
            updateUI(null)
        }
    }

    // Firebase sign out and Google revoke
    private fun revokeAccess() {
        firebaseAuth.signOut()

        // Google revoke access
        googleSignInClient.revokeAccess().addOnCompleteListener(getActivity()) {
            updateUI(null)
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {

    }
}