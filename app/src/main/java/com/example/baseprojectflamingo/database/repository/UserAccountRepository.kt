package com.example.baseprojectflamingo.database.repository

import com.example.baseprojectflamingo.database.dao.UserAccountDao
import com.example.baseprojectflamingo.database.entities.UserAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserAccountRepository(
    private val userAccountDao: UserAccountDao
) {

    suspend fun insertAccount(user: UserAccount) =
        withContext(Dispatchers.IO) {
            userAccountDao.insertAccount(user)
        }

    fun getAllAccounts() = userAccountDao.getAllAccounts()

    suspend fun updateAccount(user: UserAccount) =
        withContext(Dispatchers.IO) {
            userAccountDao.updateAccount(user)
        }

    suspend fun deleteAccount(user: UserAccount) =
        withContext(Dispatchers.IO) {
            userAccountDao.deleteAccount(user)
        }

    suspend fun searchAccountByEmailAndPassword(email: String, password: String) =
        withContext(Dispatchers.IO) {
            userAccountDao.searchAccountByEmailAndPassword(email, password)
        }

    suspend fun isEmailAlreadyExist(email: String) = withContext(Dispatchers.IO) {
        userAccountDao.isEmailAlreadyExist(email) != null
    }

}