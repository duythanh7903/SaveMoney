package com.example.baseprojectflamingo.ui.screen.frg.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojectflamingo.database.entities.NotificationEntity
import com.example.baseprojectflamingo.database.repository.ActualCostRepository
import com.example.baseprojectflamingo.database.repository.EstCostRepository
import com.example.baseprojectflamingo.database.repository.ExpectedIncomeRepository
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import com.example.baseprojectflamingo.database.repository.RealIncomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ChartViewModel(
    private val realIncomeRepo: RealIncomeRepository,
    private val actualCostRepo: ActualCostRepository,
    private val expectedIncomeRepo: ExpectedIncomeRepository,
    private val estCostRepo: EstCostRepository,
    private val notiRepo: NotificationRepository
): ViewModel() {

    val listRecordRealIncomeToday = realIncomeRepo.getAllRecordsRealIncome()
    val listRecordActualCostToday = actualCostRepo.getAllRecordActualCosts()

    val realIncomeLast12Months = realIncomeRepo.getCostsLast12Months()
    val expectedIncomeLast12Months = expectedIncomeRepo.getRevenueLast12Months()
    val actualCostLast12Months = actualCostRepo.getCostsLast12Months()
    val estCostLast12Months = estCostRepo.getCostsLast12Months()

    fun saveNoti(noti: NotificationEntity) = viewModelScope.launch(Dispatchers.IO) {
        notiRepo.saveNotification(noti)
        cancel()
    }


}