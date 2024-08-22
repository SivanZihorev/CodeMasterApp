package com.example.codemasterapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.codemasterapp.databinding.ActivityCreateProfileBinding
import com.example.codemasterapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class CreateProfileActivity : AppCompatActivity() {

    lateinit var profileBinding: ActivityCreateProfileBinding
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = ActivityCreateProfileBinding.inflate(layoutInflater)
        val view = profileBinding.root
        enableEdgeToEdge()
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}