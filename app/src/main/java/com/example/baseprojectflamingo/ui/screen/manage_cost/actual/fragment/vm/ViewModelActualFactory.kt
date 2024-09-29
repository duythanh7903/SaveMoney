package com.example.baseprojectflamingo.ui.screen.manage_cost.actual.fragment.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.database.repository.ActivitiesRepository
import com.example.baseprojectflamingo.database.repository.ActualCostRepository
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository

class ViewModelActualFactory(
    private val parentCateRepo: ParentCategoryRepository,
    private val childCateRepo: ChildCategoryRepository,
    private val actualCostRepo: ActualCostRepository,
    private val activitiesRepo: ActivitiesRepository,
    private val notiRepo: NotificationRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActualViewModel::class.java))
            return ActualViewModel(parentCateRepo, childCateRepo, actualCostRepo, activitiesRepo, notiRepo) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}