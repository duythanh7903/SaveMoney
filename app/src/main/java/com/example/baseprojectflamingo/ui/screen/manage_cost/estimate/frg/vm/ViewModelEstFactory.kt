package com.example.baseprojectflamingo.ui.screen.manage_cost.estimate.frg.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.EstCostRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository

class ViewModelEstFactory(
    private val parentCateRepo: ParentCategoryRepository,
    private val childCatRepo: ChildCategoryRepository,
    private val estCostRepo: EstCostRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstViewModel::class.java))
            return EstViewModel(parentCateRepo, childCatRepo, estCostRepo) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}