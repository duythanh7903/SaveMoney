package com.example.baseprojectflamingo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.baseprojectflamingo.database.entities.UserAccount

@Dao
interface UserAccountDao {

    @Insert
    fun insertAccount(account: UserAccount)

    @Query("SELECT * FROM USER_ACCOUNT ORDER BY createdAt DESC")
    fun getAllAccounts(): LiveData<MutableList<UserAccount>>

    @Update
    fun updateAccount(account: UserAccount)

    @Delete
    fun deleteAccount(account: UserAccount)


    @Query("SELECT * FROM USER_ACCOUNT WHERE email == :email AND password == :password")
    fun searchAccountByEmailAndPassword(email: String, password: String): UserAccount?

    @Query("SELECT * FROM USER_ACCOUNT WHERE email = :email")
    fun isEmailAlreadyExist(email: String): UserAccount?

}