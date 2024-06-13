package com.submission.mytax.loginregister

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView
import com.submission.mytax.AddUsahaActivity
import com.submission.mytax.R

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ambil nama dari SharedPreferences
        val sharedPreferences = getSharedPreferences("MyTaxPrefs", MODE_PRIVATE)
        val username =
            sharedPreferences.getString("USERNAME", "John Doe") // Default value adalah "John Doe"

        // Menampilkan nama di TextView
        val textViewName = findViewById<TextView>(R.id.textViewUsername)
        textViewName.text = username

        // Setup Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar_mytax)
        setSupportActionBar(toolbar)

        // Hide default title
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Inisialisasi Menu
        val btnMenu3 = findViewById<View>(R.id.btnMenu3)
        btnMenu3.setOnClickListener {
            startActivity(Intent(this, PajakActivity::class.java))
        }
        // ... tambahkan OnClickListener untuk menu lainnya

        // Inisialisasi DrawerLayout
        drawerLayout = findViewById(R.id.drawerLayout)

        // Inisialisasi ActionBarDrawerToggle
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ).apply {
            drawerArrowDrawable.color =
                resources.getColor(R.color.black) // Sesuaikan dengan warna yang diinginkan
        }

        // Set ActionBarDrawerToggle sebagai listener untuk DrawerLayout
        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        // Sinkronisasi status ActionBarDrawerToggle dengan DrawerLayout
        actionBarDrawerToggle.syncState()

        // Setup side menu (Navigation Drawer)
        val navView: NavigationView = findViewById(R.id.navView)

        navView.setNavigationItemSelectedListener { menuItem ->
            // Handle navigation item clicks here.
            when (menuItem.itemId) {
                R.id.BtnBack -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.nav_usaha -> {
                    // Handle the usaha action
                    startActivity(Intent(this, AddUsahaActivity::class.java))
                    true
                }

                R.id.nav_darkmode -> {
                    // Handle the darkmode action
                    true
                }

                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return super.onOptionsItemSelected(item)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onBackPressed() {
        // Menutup aplikasi ketika tombol kembali ditekan
        super.onBackPressed()
        finishAffinity() // Menutup semua aktivitas di stack dan aplikasi
    }
}
