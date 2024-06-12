package com.submission.mytax.loginregister.di

import android.content.Context
import com.submission.mytax.loginregister.data.UserRepository
import com.submission.mytax.loginregister.data.pref.UserPreference
import com.submission.mytax.loginregister.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}