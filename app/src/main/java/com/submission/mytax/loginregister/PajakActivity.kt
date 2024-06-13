package com.submission.mytax.loginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.submission.mytax.R

class PajakActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var cardPPN: CardView
    private lateinit var cardPPh22: CardView
    private lateinit var cardPPh23: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pajak)

        // Setup Toolbar
        toolbar = findViewById(R.id.toolbar_pajak)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Back button functionality
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Initialize CardViews
        cardPPN = findViewById(R.id.card_ppn)
        cardPPh22 = findViewById(R.id.card_pph22)
        cardPPh23 = findViewById(R.id.card_pph23)

        // Set OnClickListener for CardViews
        cardPPN.setOnClickListener {
            navigateToPPNActivity()
        }

        cardPPh22.setOnClickListener {
            navigateToPPh22Activity()
        }

        cardPPh23.setOnClickListener {
            navigateToPPh23Activity()
        }
    }

    private fun navigateToPPNActivity() {
        val intent = Intent(this, PPNActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToPPh22Activity() {
        val intent = Intent(this, PPh22Activity::class.java)
        startActivity(intent)
    }

    private fun navigateToPPh23Activity() {
        val intent = Intent(this, PPh23Activity::class.java)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
