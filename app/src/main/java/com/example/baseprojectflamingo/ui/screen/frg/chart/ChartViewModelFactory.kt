package com.example.baseprojectflamingo.ui.screen.frg.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.database.repository.ActualCostRepository
import com.example.baseprojectflamingo.database.repository.EstCostRepository
import com.example.baseprojectflamingo.database.repository.ExpectedIncomeRepository
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import com.example.baseprojectflamingo.database.repository.RealIncomeRepository

class ChartViewModelFactory(
    private val realIncomeRepo: RealIncomeRepository,
    private val actualCostRepo: ActualCostRepository,
    private val expectedIncomeRepo: ExpectedIncomeRepository,
    private val estCostRepo: EstCostRepository,
    private val notiRepo: NotificationRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChartViewModel::class.java))
            return ChartViewModel(realIncomeRepo, actualCostRepo, expectedIncomeRepo, estCostRepo, notiRepo) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}