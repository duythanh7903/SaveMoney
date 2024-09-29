package com.example.baseprojectflamingo.ui.screen.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.database.repository.NotificationRepository

class NotiViewModelFactory(
    private val repository: NotificationRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotiViewModel::class.java))
            return NotiViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}