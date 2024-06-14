package com.submission.mytax

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.submission.mytax.loginregister.LoginActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_profile)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Back button functionality
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyTaxPrefs", MODE_PRIVATE)

        // Setup UI elements
        val editProfileIcon = findViewById<ImageButton>(R.id.editProfileIcon)
        editProfileIcon.setOnClickListener {
            navigateToEditProfile()
        }

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            logout()
        }

        // Display user data including profile image
        displayUserData()
    }

    override fun onResume() {
        super.onResume()
        // Refresh user data including profile image
        displayUserData()
    }

    private fun displayUserData() {
        val userNameTextView = findViewById<TextView>(R.id.name)
        val userNPWPTextView = findViewById<TextView>(R.id.npwp)
        val userEmailTextView = findViewById<TextView>(R.id.email)
        val userPhoneTextView = findViewById<TextView>(R.id.phone)
        val userDirectorTextView = findViewById<TextView>(R.id.director)
        val userAddressTextView = findViewById<TextView>(R.id.address)
        val profileImageView = findViewById<ImageView>(R.id.profile_image)

        // Retrieve data from SharedPreferences
        val userName = sharedPreferences.getString("USERNAME", "")
        val userNPWP = sharedPreferences.getString("NPWP", "")
        val userEmail = sharedPreferences.getString("EMAIL", "")
        val userPhone = sharedPreferences.getString("PHONE", "")
        val userDirector = sharedPreferences.getString("DIRECTOR", "")
        val userAddress = sharedPreferences.getString("ADDRESS", "")

        // Set data to TextViews
        userNameTextView.text = userName
        userNPWPTextView.text = userNPWP
        userEmailTextView.text = userEmail
        userPhoneTextView.text = userPhone
        userDirectorTextView.text = userDirector
        userAddressTextView.text = userAddress

        // Load profile image if available
        val profileImageUriString = sharedPreferences.getString("PROFILE_IMAGE_URI", null)
        if (profileImageUriString != null) {
            val profileImageUri = Uri.parse(profileImageUriString)
            profileImageView.setImageURI(profileImageUri)
        }
    }

    private fun navigateToEditProfile() {
        val intent = Intent(this, EditProfileActivity::class.java)
        startActivity(intent)
    }

    private fun logout() {
        clearSession()

        // Navigate to Welcome or Login page after logout
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // Close ProfileActivity to prevent access using back button
    }

    private fun clearSession() {
        // Clear all data stored in SharedPreferences
        sharedPreferences.edit().clear().apply()
    }
}
