package com.submission.mytax

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.submission.mytax.databinding.ActivityAddPenjualanBinding
import com.submission.mytax.model.Penjualan

class AddPenjualanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPenjualanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPenjualanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_add_penjualan)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Back button functionality
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnNext.setOnClickListener {
            val namaPembeli = binding.edNamaPembeli.text.toString()
            val npwpPembeli = binding.edNPWPpembeli.text.toString()
            val faktur = binding.edFaktur.text.toString()
            val tanggal = binding.edTanggal.text.toString()
            val total = binding.edTotal.text.toString()
            val ppn = binding.edPPNKeluarran.text.toString()
            val typePenjualan = if (binding.rbDalamNegeri.isChecked) "Dalam Negeri" else "Luar Negeri"
            val ppnIncluded = if (binding.rbPPN.isChecked) "Ya" else "Tidak"

            val intent = Intent()
            val penjualan = Penjualan(namaPembeli, npwpPembeli, faktur, tanggal, total, ppn)
            intent.putExtra("penjualan", penjualan)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
