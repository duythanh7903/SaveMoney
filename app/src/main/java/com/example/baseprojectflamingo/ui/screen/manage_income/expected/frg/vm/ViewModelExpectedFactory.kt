package com.example.baseprojectflamingo.ui.screen.manage_income.expected.frg.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.database.repository.ChildCategoryRepository
import com.example.baseprojectflamingo.database.repository.ExpectedIncomeRepository
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository

class ViewModelExpectedFactory(
    private val parentCateRepo: ParentCategoryRepository,
    private val childCatRepo: ChildCategoryRepository,
    private val expectedIncomeRepo: ExpectedIncomeRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpectedViewModel::class.java))
            return ExpectedViewModel(parentCateRepo, childCatRepo,expectedIncomeRepo) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}