package com.example.baseprojectflamingo.ui.screen.frg.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojectflamingo.database.entities.UserAccount
import com.example.baseprojectflamingo.database.repository.UserAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(
    private val repository: UserAccountRepository
): ViewModel() {

    fun updateAccount(user: UserAccount) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateAccount(user)
    }

}