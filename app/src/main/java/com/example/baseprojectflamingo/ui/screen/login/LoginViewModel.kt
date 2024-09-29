package com.example.baseprojectflamingo.ui.screen.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojectflamingo.base.ext.convertObjectToJson
import com.example.baseprojectflamingo.commons.PreferencesUtils
import com.example.baseprojectflamingo.database.entities.UserAccount
import com.example.baseprojectflamingo.database.repository.UserAccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("StaticFieldLeak")
class LoginViewModel(
    private val repository: UserAccountRepository,
    private val context: Context
): ViewModel() {

    fun loginAccount(
        user: UserAccount,
        onLoginError: () -> Unit,
        onLoginSuccess: () -> Unit
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            val accountAfterSearch =
                repository.searchAccountByEmailAndPassword(user.email, user.password)
            if (accountAfterSearch == null) {
                withContext(Dispatchers.Main) {
                    onLoginError.invoke()
                }
            } else {
                cacheAccountTypeJsonToShared(accountAfterSearch)
                withContext(Dispatchers.Main) {
                    onLoginSuccess.invoke()
                }
            }

            cancel()
        }

    private fun cacheAccountTypeJsonToShared(account: UserAccount) {
        val json = context.convertObjectToJson(account)
        PreferencesUtils.jsonAccount = json
    }

}