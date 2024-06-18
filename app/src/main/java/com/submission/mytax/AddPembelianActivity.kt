package com.submission.mytax

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.submission.mytax.databinding.ActivityAddPembelianBinding
import com.submission.mytax.model.Pembelian
import com.submission.mytax.model.Penjualan

class AddPembelianActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPembelianBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPembelianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_add_pembelian)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Back button functionality
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            val namaPenjual = binding.edNamaPenjual.text.toString()
            val npwpPenjual = binding.edNPWPpenjual.text.toString()
            val noFaktur = binding.edFaktur.text.toString()
            val tanggal = binding.edTanggal.text.toString()
            val total = binding.edTotal.text.toString().toDoubleOrNull() ?: 0.0
            val ppn = binding.edPPNMasukan.text.toString().toDoubleOrNull() ?: 0.0
            val ppnDapatDikreditkan = binding.rbKredit.isChecked
            val hargaTermasukPPN = binding.rbPPN.isChecked

            val intent = Intent()
            val pembelian = Pembelian(namaPenjual, npwpPenjual, noFaktur, tanggal, total, ppn)
            intent.putExtra("pembelian", pembelian)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
