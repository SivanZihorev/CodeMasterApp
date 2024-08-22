package com.example.codemasterapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.codemasterapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    lateinit var signUpBinding: ActivitySignUpBinding
    val auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = signUpBinding.root
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        signUpBinding.buttonSignup.setOnClickListener {

            val email = signUpBinding.editTextSignupEmail.text.toString()
            val password = signUpBinding.editTextSignupPassword.text.toString()

            signupWithFirebase(email, password)
        }
    }

    fun signupWithFirebase(email: String, password: String){
        signUpBinding.progressBarSignup.visibility = View.VISIBLE
        signUpBinding.buttonSignup.isClickable = false

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                // Get the newly created user's ID
                val userId = auth.currentUser?.uid

                // Create a new user document in Firestore
                userId?.let {
                    val user = hashMapOf(
                        "username" to "", // You can set default values or leave it empty
                        "phone" to "" // You can set default values or leave it empty
                    )

                    FirebaseFirestore.getInstance().collection("users").document(it)
                        .set(user)
                        .addOnSuccessListener {
                            // Successfully created user document
                            Toast.makeText(applicationContext, "Your account has been created", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            // Failed to create user document
                            Toast.makeText(applicationContext, "Failed to create user document: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }

                finish()
                signUpBinding.progressBarSignup.visibility = View.INVISIBLE
                signUpBinding.buttonSignup.isClickable = true
            }
            else{
                Toast.makeText(applicationContext, task.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }


}