package com.example.googlesigninfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.googlesigninfirebase.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {


    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

/*    // Custom activity result contract
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                Log.d(TAG, "onActivityResult: Google SignIn intent Result")
                val accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (accountTask.isSuccessful) {
                    Log.d(TAG, "onActivityResult: accountTask.isSuccessful")
                    try {
                        val account = accountTask.getResult(ApiException::class.java)
                        if (account != null){
                            // Initialize auth credential
                            firebaseAuthWithGoogleAccount(account)
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "onActivityResult: ${e.message}")
                    }
                }

        }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.googleSignInButton.setOnClickListener {
            Log.d(TAG, "onCreate: begin google sign in")
            val intent = googleSignInClient.signInIntent
            //startActivityForResult(intent, RC_SIGN_IN)
            launcher.launch(intent)
        }
        checkUser()*/
    }

/*    private fun checkUser() {
//check if user loggedin or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user is already logged in
            val intent = Intent(this, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            this.finish()
            Log.d(TAG, "checkUser: user already logged in ${firebaseUser.email}")
        }
    }*/

/*    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth with google account")

        val credentials = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credentials).addOnSuccessListener { authResult ->
            //login success

            Log.d(TAG, "firebaseAuthWithGoogleAccount: LoggedIn")

            //get loggedIn user
            val firebaseUser = firebaseAuth.currentUser

            //get user info
            val uid = firebaseUser!!.uid
            val email = firebaseUser.email
            Log.d(TAG, "firebaseAuthWithGoogleAccount: Uid = $uid")
            Log.d(TAG, "firebaseAuthWithGoogleAccount: Email = $email")

            //check if user new or existing
            if (authResult.additionalUserInfo!!.isNewUser) {
                //user is new -account created
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Account Created... \n$email")
                Toast.makeText(this, "Account created...\n$email", Toast.LENGTH_SHORT)
                    .show()
            } else {
                //existing user -- logged in
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Existing User... \n$email")
                Toast.makeText(this, "Logged in...\n$email", Toast.LENGTH_SHORT).show()
            }
            // start profile activity
            val intent = Intent(this, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            this.finish()
        }
            .addOnFailureListener { e ->
                //login failed
                Log.d(TAG, "firebaseAuthWithGoogleAccount: LoggedIn Failed due to ${e.message}")
                Toast.makeText(
                    this,
                    "LoggedIn Failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }*/


    companion object {
        const val TAG = "MainActivity"
        private const val RC_SIGN_IN = 5
    }
}