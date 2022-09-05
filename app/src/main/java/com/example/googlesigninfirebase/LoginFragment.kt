package com.example.googlesigninfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.googlesigninfirebase.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {

private lateinit var binding: FragmentLoginBinding

    // Custom activity result contract
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(MainActivity.TAG, "onActivityResult: Google SignIn intent Result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if (accountTask.isSuccessful) {
                Log.d(MainActivity.TAG, "onActivityResult: accountTask.isSuccessful")
                try {
                    val account = accountTask.getResult(ApiException::class.java)
                    if (account != null){
                        // Initialize auth credential
                        firebaseAuthWithGoogleAccount(account)
                    }
                } catch (e: Exception) {
                    Log.d(MainActivity.TAG, "onActivityResult: ${e.message}")
                }
            }

        }

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        firebaseAuth = FirebaseAuth.getInstance()


        checkUser()
    }


    private fun checkUser() {
//check if user loggedin or not
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user is already logged in
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            requireActivity().finish()
            Log.d(MainActivity.TAG, "checkUser: user already logged in ${firebaseUser.email}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.googleSignInButton.setOnClickListener {
            Log.d(MainActivity.TAG, "onCreate: begin google sign in")
            val intent = googleSignInClient.signInIntent
            //startActivityForResult(intent, RC_SIGN_IN)
            launcher.launch(intent)
        }

        return binding.root
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(MainActivity.TAG, "firebaseAuthWithGoogleAccount: begin firebase auth with google account")

        val credentials = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credentials).addOnSuccessListener { authResult ->
            //login success

            Log.d(MainActivity.TAG, "firebaseAuthWithGoogleAccount: LoggedIn")

            //get loggedIn user
            val firebaseUser = firebaseAuth.currentUser

            //get user info
            val uid = firebaseUser!!.uid
            val email = firebaseUser.email
            Log.d(MainActivity.TAG, "firebaseAuthWithGoogleAccount: Uid = $uid")
            Log.d(MainActivity.TAG, "firebaseAuthWithGoogleAccount: Email = $email")

            //check if user new or existing
            if (authResult.additionalUserInfo!!.isNewUser) {
                //user is new -account created
                Log.d(MainActivity.TAG, "firebaseAuthWithGoogleAccount: Account Created... \n$email")
                Toast.makeText(requireContext(), "Account created...\n$email", Toast.LENGTH_SHORT)
                    .show()
            } else {
                //existing user -- logged in
                Log.d(MainActivity.TAG, "firebaseAuthWithGoogleAccount: Existing User... \n$email")
                Toast.makeText(requireContext(), "Logged in...\n$email", Toast.LENGTH_SHORT).show()
            }
            // start profile activity
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            requireActivity().finish()
        }
            .addOnFailureListener { e ->
                //login failed
                Log.d(MainActivity.TAG, "firebaseAuthWithGoogleAccount: LoggedIn Failed due to ${e.message}")
                Toast.makeText(
                    requireContext(),
                    "LoggedIn Failed due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

}