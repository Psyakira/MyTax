package com.submission.mytax.loginregister

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.submission.mytax.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "User")

        findViewById<TextView>(R.id.textViewWelcome).text = "Welcome, $userName!"
    }
}
