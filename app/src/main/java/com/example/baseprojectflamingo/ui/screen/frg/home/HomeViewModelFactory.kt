package com.example.baseprojectflamingo.ui.screen.frg.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.database.repository.ActivitiesRepository
import com.example.baseprojectflamingo.database.repository.NotificationRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val repository: ActivitiesRepository,
    private val notiRepo: NotificationRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(repository, notiRepo) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}