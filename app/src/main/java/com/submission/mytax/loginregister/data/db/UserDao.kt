package com.submission.mytax.loginregister.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateUser(user: User)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): User?

    @Query("SELECT * FROM user WHERE name = :name LIMIT 1")
    fun getUserByUsername(name: String): User?
}

