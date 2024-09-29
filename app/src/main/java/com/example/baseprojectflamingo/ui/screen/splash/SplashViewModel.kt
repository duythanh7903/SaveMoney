package com.example.baseprojectflamingo.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojectflamingo.database.entities.ParentCategory
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: ParentCategoryRepository
): ViewModel() {

    val listCategoryParent = repository.getAllCategoryParents()

    fun insertAllCategory(list: MutableList<ParentCategory>) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAllCategoryParent(list)
            cancel()
        }
}