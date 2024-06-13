package com.submission.mytax.loginregister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.submission.mytax.R
import com.submission.mytax.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.nextButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("MyTaxPrefs", Context.MODE_PRIVATE)
            val userName = sharedPreferences.getString("USERNAME", "")

            val targetActivity = if (userName.isNullOrEmpty()) {
                Intent(this, LoginActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java).apply {
                    putExtra("NAME", userName)
                }
            }

            startActivity(targetActivity)
            finish()
        }
    }
}
