package com.submission.mytax

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.submission.mytax.databinding.ActivityDetailPembelianBinding
import com.submission.mytax.model.Pembelian
import java.text.NumberFormat
import java.util.*

class DetailPembelianActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPembelianBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPembelianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_detail_pembelian)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Back button functionality
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        loadPembelianDetail()

        binding.btnSave.setOnClickListener {
            navigateToPembelianActivity()
        }
    }

    private fun navigateToPembelianActivity() {
        val intent = Intent(this, PembelianActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish() // Optional: Call finish() if DetailPembelianActivity should be closed
    }

    private fun loadPembelianDetail() {
        val pembelian = intent.getParcelableExtra<Pembelian>("pembelian")

        pembelian?.let {
            binding.tvNama.text = it.namaPenjual
            binding.tvNPWP.text = it.npwpPenjual
            binding.tvFaktur.text = it.noFaktur
            binding.tvTanggal.text = it.tanggal
            binding.tvTotal.text = formatRupiah(it.total ?: 0.0)

            val total = it.total ?: 0.0
            val ppn = it.ppnMasukan ?: 0.0

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
