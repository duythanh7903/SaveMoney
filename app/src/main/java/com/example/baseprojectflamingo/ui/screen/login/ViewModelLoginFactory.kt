package com.example.baseprojectflamingo.ui.screen.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.database.repository.UserAccountRepository

@Suppress("UNCHECKED_CAST")
class ViewModelLoginFactory(
    private val repository: UserAccountRepository,
    private val context: Context
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java))
            return LoginViewModel(repository, context) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}