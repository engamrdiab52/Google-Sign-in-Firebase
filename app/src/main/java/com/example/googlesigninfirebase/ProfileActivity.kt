package com.example.googlesigninfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.googlesigninfirebase.MainActivity.Companion.TAG
import com.example.googlesigninfirebase.databinding.ActivityMainBinding
import com.example.googlesigninfirebase.databinding.ActivityProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            Log.d(TAG, "onCreate:(firebaseUser != null) ${firebaseUser.displayName} ")
        }
        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)

        binding.buttonSignOut.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                if (it.isSuccessful){
                    firebaseAuth.signOut()
                    Log.d(TAG, "onCreate: firebaseAuth.signOut() : Logged out successfully")
                    val intent = Intent(this, MainActivity::class.java)
                    Toast.makeText(this, "Logging Out", Toast.LENGTH_SHORT).show()
                    startActivity(intent)

                    finish()
                }
            }
        }
    }

    companion object {
        const val ID = "id"
    }
}