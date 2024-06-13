package com.submission.mytax.loginregister

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.submission.mytax.R

class LoginActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Find the TextView for "Login as Guest"
        val tvLoginGuest: TextView = findViewById(R.id.tvLoginGuest)

        // Set OnClickListener on the "Login as Guest" TextView
        tvLoginGuest.setOnClickListener {
            // Create an intent to start the target activity
            val intent = Intent(this, NameActivity::class.java)
            startActivity(intent)
        }

        // Konfigurasi Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Tombol Google Sign-In
        findViewById<Button>(R.id.btnGoogleSignIn).setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                // Masuk berhasil, lakukan sesuatu dengan informasi akun
                updateUI(account)
            } catch (e: ApiException) {
                // Masuk gagal, berikan informasi kesalahan kepada pengguna
                updateUI(null)
            }
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            // Misalnya, arahkan pengguna ke aktivitas utama
            Intent(this, WelcomeActivity::class.java).also {
                startActivity(it)
                finish()
            }
        } else {
            // Tampilkan pesan kesalahan atau lakukan sesuatu jika login gagal
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
        }
    }
}
