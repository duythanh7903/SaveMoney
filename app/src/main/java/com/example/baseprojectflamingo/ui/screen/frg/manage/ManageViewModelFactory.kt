package com.example.baseprojectflamingo.ui.screen.frg.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.database.repository.ActualCostRepository
import com.example.baseprojectflamingo.database.repository.EstCostRepository
import com.example.baseprojectflamingo.database.repository.ExpectedIncomeRepository
import com.example.baseprojectflamingo.database.repository.RealIncomeRepository

class ManageViewModelFactory(
    private val realIncomeRepo: RealIncomeRepository,
    private val actualCostRepo: ActualCostRepository,
    private val expectedIncomeRepo: ExpectedIncomeRepository,
    private val estCostRepo: EstCostRepository,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManageViewModel::class.java))
            return ManageViewModel(realIncomeRepo, actualCostRepo, expectedIncomeRepo, estCostRepo) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}