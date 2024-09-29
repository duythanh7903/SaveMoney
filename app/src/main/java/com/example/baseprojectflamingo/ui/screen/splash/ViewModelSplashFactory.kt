package com.example.baseprojectflamingo.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository

class ViewModelSplashFactory(
    private val repository: ParentCategoryRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java))
            return SplashViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}