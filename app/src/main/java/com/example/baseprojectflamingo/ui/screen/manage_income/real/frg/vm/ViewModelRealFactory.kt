package com.example.baseprojectflamingo.ui.screen.manage_income.real.frg.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.database.repository.ActivitiesRepository
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository
import com.example.baseprojectflamingo.database.repository.RealIncomeRepository

class ViewModelRealFactory(
    private val parentCateRepo: ParentCategoryRepository,
    private val childCateRepo: ChildCategoryRepository,
    private val realIncomeRepo: RealIncomeRepository,
    private val activitiesRepo: ActivitiesRepository,
    private val notificationsRepo: NotificationRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RealViewModel::class.java))
            return RealViewModel(parentCateRepo, childCateRepo, realIncomeRepo, activitiesRepo, notificationsRepo) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}