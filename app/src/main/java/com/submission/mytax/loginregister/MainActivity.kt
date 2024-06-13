package com.submission.mytax.loginregister

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView
import com.submission.mytax.R

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        // Hide default title
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Set custom title
        val toolbarTitle: TextView = findViewById(R.id.toolbar_title)
        toolbarTitle.text = "MyTax"

        // Inisialisasi Menu
        val btnMenu4 = findViewById<View>(R.id.btnMenu4)
        btnMenu4.setOnClickListener {
            startActivity(Intent(this, KonsultasiActivity::class.java))
        }

        val btnMenu5 = findViewById<View>(R.id.btnMenu5)
        btnMenu5.setOnClickListener {
            startActivity(Intent(this, NPWPActivity::class.java))
        }

        // Inisialisasi DrawerLayout
        drawerLayout = findViewById(R.id.drawerLayout)

        // Inisialisasi ActionBarDrawerToggle
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

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
}
