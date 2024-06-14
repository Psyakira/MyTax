package com.submission.mytax

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.submission.mytax.databinding.ActivityEditProfileBinding
import android.Manifest

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    private val PICK_IMAGE_REQUEST = 1
    private val REQUEST_IMAGE_CAPTURE = 2
    private val REQUEST_PERMISSION_CODE = 100
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_edit_profile)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Back button functionality
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.profileImage.setOnClickListener {
            showImagePickerDialog()
        }

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("MyTaxPrefs", MODE_PRIVATE)

        // Mendapatkan nilai dari SharedPreferences
        val userName = sharedPreferences.getString("USERNAME", "") ?: ""
        val userNPWP = sharedPreferences.getString("NPWP", "") ?: ""
        val userEmail = sharedPreferences.getString("EMAIL", "") ?: ""
        val userPhone = sharedPreferences.getString("PHONE", "") ?: ""
        val userDirector = sharedPreferences.getString("DIRECTOR", "") ?: ""
        val userAddress = sharedPreferences.getString("ADDRESS", "") ?: ""

        // Menampilkan gambar profil dari SharedPreferences
        val profileImageUri = sharedPreferences.getString("PROFILE_IMAGE_URI", null)
        if (profileImageUri != null) {
            binding.profileImage.setImageURI(Uri.parse(profileImageUri))
        }

        // Menampilkan nilai di EditText
        binding.name.setText(userName)
        binding.edittextNpwp.setText(userNPWP)
        binding.email.setText(userEmail)
        binding.phone.setText(userPhone)
        binding.director.setText(userDirector)
        binding.alamat.setText(userAddress)

        // Setup onClickListener untuk tombol simpan
        binding.btnSaveProfile.setOnClickListener {
            simpanPerubahanProfil()
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Ambil dari Galeri", "Ambil Foto dengan Kamera", "Batal")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih Foto Profil")

        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> openGallery()
                1 -> openCamera()
                else -> dialog.dismiss()
            }
        }

        builder.show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun openCamera() {
        val values = ContentValues()
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values) ?: return
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    imageUri = data?.data ?: return
                    binding.profileImage.setImageURI(imageUri)
                    // Simpan URI gambar ke SharedPreferences
                    saveProfileImageUri(imageUri.toString())
                }
                REQUEST_IMAGE_CAPTURE -> {
                    binding.profileImage.setImageURI(imageUri)
                    // Simpan URI gambar ke SharedPreferences
                    saveProfileImageUri(imageUri.toString())
                }
            }
        }
    }

    private fun saveProfileImageUri(uri: String) {
        sharedPreferences.edit().apply {
            putString("PROFILE_IMAGE_URI", uri)
            apply()
        }
    }

    private fun simpanPerubahanProfil() {
        // Mendapatkan nilai dari EditText
        val newName = binding.name.text.toString().trim()
        val newNpwp = binding.edittextNpwp.text.toString().trim()
        val newEmail = binding.email.text.toString().trim()
        val newPhone = binding.phone.text.toString().trim()
        val newDirector = binding.director.text.toString().trim()
        val newAddress = binding.alamat.text.toString().trim()

        // Simpan nilai baru ke SharedPreferences
        sharedPreferences.edit().apply {
            putString("USERNAME", newName)
            putString("NPWP", newNpwp)
            putString("EMAIL", newEmail)
            putString("PHONE", newPhone)
            putString("DIRECTOR", newDirector)
            putString("ADDRESS", newAddress)
            apply()
        }

        Toast.makeText(this, "Perubahan disimpan", Toast.LENGTH_SHORT).show()
        // Kembali ke ProfileActivity atau halaman sebelumnya
        setResult(Activity.RESULT_OK)
        finish()
    }
}
