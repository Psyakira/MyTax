package com.submission.mytax.loginregister.data.db


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val profileImage: String,
    val name: String,
    val npwp: String,
    val email: String,
    val phone: String,
    val director: String,
    val address: String
)