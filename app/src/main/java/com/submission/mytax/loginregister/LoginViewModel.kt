package com.submission.mytax.loginregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.submission.mytax.loginregister.data.UserRepository
import com.submission.mytax.loginregister.data.pref.UserModel

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}