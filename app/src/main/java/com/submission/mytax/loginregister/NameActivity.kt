package com.submission.mytax.loginregister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.submission.mytax.R

class NameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        findViewById<Button>(R.id.btnSubmitName).setOnClickListener {
            val name = findViewById<EditText>(R.id.editTextName).text.toString()
            if (name.isNotEmpty()) {
                saveUserName(name)
                // Arahkan ke halaman home setelah pengisian nama
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            } else {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserName(name: String) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", name)
        editor.apply()
    }
}
