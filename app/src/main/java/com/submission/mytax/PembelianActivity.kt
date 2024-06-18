package com.submission.mytax

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.mytax.adapter.PembelianAdapter
import com.submission.mytax.databinding.ActivityPembelianBinding
import com.submission.mytax.model.Pembelian

class PembelianActivity : AppCompatActivity(), PembelianAdapter.OnItemClickListener {
    private lateinit var binding: ActivityPembelianBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var pembelianAdapter: PembelianAdapter
    private var pembelianList: MutableList<Pembelian> = mutableListOf()

    companion object {
        const val ADD_PEMBELIAN_REQUEST_CODE = 1
        const val PREFS_NAME = "PembelianPrefs"
        const val PEMBELIAN_KEY = "pembelian"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembelianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_pembelian)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Back button functionality
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        pembelianAdapter = PembelianAdapter(pembelianList, this)
        binding.recyclerViewPembelian.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPembelian.adapter = pembelianAdapter

        loadPembelianData()

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddPembelianActivity::class.java)
            startActivityForResult(intent, ADD_PEMBELIAN_REQUEST_CODE)
        }
    }

    override fun onResume() {
        super.onResume()
        loadPembelianData()
    }

    private fun loadPembelianData() {
        val allPembelian = sharedPreferences.getStringSet(PEMBELIAN_KEY, setOf()) ?: setOf()

        pembelianList.clear()
        allPembelian.forEach { json ->
            val pembelian = Pembelian.fromJson(json)
            pembelian?.let {
                pembelianList.add(it)
            }
        }
        pembelianAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(pembelian: Pembelian) {
        val intent = Intent(this, DetailPembelianActivity::class.java)
        intent.putExtra("pembelian", pembelian)
        startActivity(intent)
    }

    override fun onDeleteClick(position: Int) {
        pembelianList.removeAt(position)
        pembelianAdapter.notifyItemRemoved(position)

        // Update data in SharedPreferences
        val allPembelian = mutableSetOf<String>()
        pembelianList.forEach {
            allPembelian.add(Pembelian.toJson(it))
        }
        with(sharedPreferences.edit()) {
            putStringSet(PEMBELIAN_KEY, allPembelian)
            apply()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_PEMBELIAN_REQUEST_CODE && resultCode == RESULT_OK) {
            val pembelian = data?.getParcelableExtra<Pembelian>("pembelian")

            pembelian?.let {
                // Update data in SharedPreferences
                val allPembelian = sharedPreferences.getStringSet(PEMBELIAN_KEY, setOf())?.toMutableSet() ?: mutableSetOf()
                allPembelian.add(Pembelian.toJson(it))

                with(sharedPreferences.edit()) {
                    putStringSet(PEMBELIAN_KEY, allPembelian)
                    apply()
                }

                // Update RecyclerView
                pembelianList.add(it)
                pembelianAdapter.updateData(pembelianList)
            }
        }
    }
}
