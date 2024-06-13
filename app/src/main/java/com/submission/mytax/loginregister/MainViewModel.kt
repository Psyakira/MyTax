package com.submission.mytax.loginregister

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.submission.mytax.loginregister.darkmode.SettingPreferences
import com.submission.mytax.loginregister.data.UserRepository
import com.submission.mytax.loginregister.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel (private val repository: UserRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}