package com.submission.mytax

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.submission.mytax.databinding.ActivityDetailPenjualanBinding
import com.submission.mytax.model.Penjualan
import java.text.NumberFormat
import java.util.*

class DetailPenjualanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPenjualanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPenjualanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_detailpenjualan)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Back button functionality
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        loadPenjualanDetail()

        binding.btnSave.setOnClickListener {
            navigateToPenjualanActivity()
        }
    }

    private fun navigateToPenjualanActivity() {
        val intent = Intent(this, PenjualanActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // Optional: Call finish() if DetailPenjualanActivity should be closed
    }

    private fun loadPenjualanDetail() {
        val penjualan = intent.getParcelableExtra<Penjualan>("penjualan")

        penjualan?.let {
            binding.tvNamaPembeli.text = it.namaPembeli
            binding.tvNPWPpembeli.text = it.npwpPembeli
            binding.tvFaktur.text = it.faktur
            binding.tvTanggal.text = it.tanggal
            binding.tvTotal.text = formatRupiah(it.total?.toDoubleOrNull() ?: 0.0)

            val total = it.total?.toDoubleOrNull() ?: 0.0
            val ppn = it.ppn?.toDoubleOrNull() ?: 0.0

            val dpp = total / (101.0 / 100.0)
            val ppnValue = total * (ppn / 100)

            binding.tvDPP.text = formatRupiah(dpp)
            binding.tvPPN.text = formatRupiah(ppnValue)
        }
    }

    private fun formatRupiah(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        return format.format(amount)
    }
}
