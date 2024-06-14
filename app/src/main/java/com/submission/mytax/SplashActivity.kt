package com.submission.mytax

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.submission.mytax.loginregister.MainActivity
import com.submission.mytax.loginregister.WelcomeActivity
import com.submission.mytax.loginregister.data.db.UserDatabase

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            checkUserStatus()
        }, 2000L)
    }

    private fun checkUserStatus() {
        val sharedPreferences = getSharedPreferences("MyTaxPrefs", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("USERNAME", "")

        val targetActivity = if (userName.isNullOrEmpty()) {
            WelcomeActivity::class.java
        } else {
            MainActivity::class.java
        }

        Intent(this, targetActivity).also {
            startActivity(it)
            finish()
        }
    }

    private fun goToNextActivity() {
        val userDatabase = UserDatabase.getInstance(this)
        val user = userDatabase.userDao().getUser()

        if (user != null) {
            // Jika data pengguna sudah ada, langsung ke MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // Jika data pengguna belum ada, ke WelcomeActivity
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
        finish()
    }
}
