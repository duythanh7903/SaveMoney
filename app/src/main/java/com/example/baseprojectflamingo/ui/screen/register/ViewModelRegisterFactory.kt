package com.example.baseprojectflamingo.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.database.repository.UserAccountRepository

@Suppress("UNCHECKED_CAST")
class ViewModelRegisterFactory(
    private val repository: UserAccountRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java))
            return RegisterViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}