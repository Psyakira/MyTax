package com.submission.mytax.loginregister

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.submission.mytax.R

class NameActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_name)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Back button functionality
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val btnSubmitName = findViewById<Button>(R.id.btnSubmitName)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("MyTaxPrefs", Context.MODE_PRIVATE)

        btnSubmitName.setOnClickListener {
            val name = editTextName.text.toString().trim()

            if (name.isNotEmpty()) {
                saveUserName(name) // Simpan nama ke SharedPreferences
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("NAME", name)
                startActivity(intent)
                finish()
            } else {
                editTextName.error = "Please enter your name"
            }
        }
    }

    private fun saveUserName(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString("USERNAME", name)
        editor.apply()
    }
}
