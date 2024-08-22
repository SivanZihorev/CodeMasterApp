package com.example.codemasterapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.codemasterapp.api.RetrofitInstance
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SettingsPageFragment : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var updateProfileButton: View

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        usernameEditText = view.findViewById(R.id.profile_username)
        phoneEditText = view.findViewById(R.id.profile_phone)
        emailEditText = view.findViewById(R.id.profile_email)
        updateProfileButton = view.findViewById(R.id.profle_update_btn)

        // Load user data when the fragment is created
        loadUserData()

        // Set up the button click listener
        updateProfileButton.setOnClickListener {
            updateUserProfile()
        }

        return view
    }

    private fun loadUserData() {
        val userId = auth.currentUser?.uid ?: return
        val userEmail = auth.currentUser?.email ?: ""

        // Set up the listener
        firestore.collection("users").document(userId)
            .addSnapshotListener { documentSnapshot, e ->
                if (e != null) {
                    Toast.makeText(context, "Failed to load user data: ${e.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val username = documentSnapshot.getString("username") ?: ""
                    val phone = documentSnapshot.getString("phone") ?: ""

                    usernameEditText.setText(username)
                    phoneEditText.setText(phone)
                    emailEditText.setText(userEmail) // Set the email field
                } else {
                    Toast.makeText(context, "No such document", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUserProfile() {
        val username = usernameEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()

        if (username.isEmpty() || username.length < 3) {
            usernameEditText.error = "Username must be at least 3 characters long"
            return
        }

        val userId = auth.currentUser?.uid ?: return

        val userUpdates = mapOf(
            "username" to username,
            "phone" to phone
        )

        firestore.collection("users").document(userId)
            .update(userUpdates)
            .addOnSuccessListener {
                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Update failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}






