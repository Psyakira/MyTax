package com.submission.mytax

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.mytax.adapter.PenjualanAdapter
import com.submission.mytax.databinding.ActivityPenjualanBinding
import com.submission.mytax.model.Penjualan

class PenjualanActivity : AppCompatActivity(), PenjualanAdapter.OnItemClickListener {
    private lateinit var binding: ActivityPenjualanBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var penjualanAdapter: PenjualanAdapter
    private var penjualanList: MutableList<Penjualan> = mutableListOf()

    companion object {
        const val ADD_PENJUALAN_REQUEST_CODE = 1
        const val PREFS_NAME = "PenjualanPrefs"
        const val PENJUALAN_KEY = "penjualan"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenjualanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_penjualan)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Back button functionality
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        penjualanAdapter = PenjualanAdapter(penjualanList, this)
        binding.recyclerViewPenjualan.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPenjualan.adapter = penjualanAdapter

        loadPenjualanData()

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddPenjualanActivity::class.java)
            startActivityForResult(intent, ADD_PENJUALAN_REQUEST_CODE)
        }
    }

    private fun loadPenjualanData() {
        val allPenjualan = sharedPreferences.getStringSet(PENJUALAN_KEY, setOf()) ?: setOf()

        penjualanList.clear()
        allPenjualan.forEach { json ->
            val penjualan = Penjualan.fromJson(json)
            penjualan?.let {
                penjualanList.add(it)
            }
        }
        penjualanAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(penjualan: Penjualan) {
        val intent = Intent(this, DetailPenjualanActivity::class.java)
        intent.putExtra("penjualan", penjualan)
        startActivity(intent)
    }

    override fun onDeleteClick(position: Int) {
        penjualanList.removeAt(position)
        penjualanAdapter.notifyItemRemoved(position)

        // Update data in SharedPreferences
        val allPenjualan = mutableSetOf<String>()
        penjualanList.forEach {
            allPenjualan.add(Penjualan.toJson(it))
        }
        with(sharedPreferences.edit()) {
            putStringSet(PENJUALAN_KEY, allPenjualan)
            apply()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PENJUALAN_REQUEST_CODE && resultCode == RESULT_OK) {
            val penjualan = data?.getParcelableExtra<Penjualan>("penjualan")

            penjualan?.let {
                val allPenjualan = sharedPreferences.getStringSet(PENJUALAN_KEY, setOf())?.toMutableSet() ?: mutableSetOf()
                allPenjualan.add(Penjualan.toJson(it))

                with(sharedPreferences.edit()) {
                    putStringSet(PENJUALAN_KEY, allPenjualan)
                    apply()
                }

                penjualanList.add(it)
                penjualanAdapter.notifyDataSetChanged()
            }
        }
    }
}
