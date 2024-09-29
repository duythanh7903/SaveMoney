package com.example.baseprojectflamingo.ui.screen.frg.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.database.repository.UserAccountRepository

class AccountVmFactory(
    private val repository: UserAccountRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java))
            return AccountViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}