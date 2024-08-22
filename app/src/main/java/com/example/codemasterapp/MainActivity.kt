package com.example.codemasterapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.codemasterapp.Quiz.QuizPage
import com.example.codemasterapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        val textViewGreeting: TextView = findViewById(R.id.textViewGreeting)
        updateGreeting(mainBinding.textViewGreeting)
        replaceFragment(CoursesPageFragment())

        mainBinding.buttonSignOut.setOnClickListener {

            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        mainBinding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.QuizTime -> replaceFragment(QuizPage())
                R.id.Settings -> replaceFragment(SettingsPageFragment())
                R.id.Courses -> replaceFragment(CoursesPageFragment())

                else ->{



                }

            }

            true

        }


    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()


    }

    private fun updateGreeting(textViewGreeting: TextView) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val username = document.getString("username") ?: "User"
                    textViewGreeting.text = "Hello, $username"
                } else {
                    Toast.makeText(this, "No such document", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load user data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}