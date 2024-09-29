package com.example.baseprojectflamingo.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojectflamingo.database.entities.UserAccount
import com.example.baseprojectflamingo.database.repository.UserAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val repository: UserAccountRepository
): ViewModel() {

    fun registerAccount(
        userAccount: UserAccount,
        onAccountAlreadyExists: () -> Unit,
        onRegisterSuccess: () -> Unit
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            val isEmailAlreadyExists =
                isEmailAlreadyExists(userAccount.email)

            if (isEmailAlreadyExists) {
                withContext(Dispatchers.Main) {
                    onAccountAlreadyExists.invoke()
                }
            } else {
                repository.insertAccount(userAccount)
                withContext(Dispatchers.Main) {
                    onRegisterSuccess.invoke()
                }
            }

            cancel()
        }

    private suspend fun isEmailAlreadyExists(email: String) =
        repository.isEmailAlreadyExist(email)

}