package com.example.baseprojectflamingo.database.repository

import com.example.baseprojectflamingo.database.dao.RecordEstimateCostDao
import com.example.baseprojectflamingo.database.entities.RecordEstimateCost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EstCostRepository(
    private val dao: RecordEstimateCostDao
) {

    suspend fun insertRecordEstimateCost(recordEstimateCost: RecordEstimateCost) =
        withContext(Dispatchers.IO) {
            dao.insertRecordEstimateCost(recordEstimateCost)
        }

    fun getAllRecordEstimateCosts() =
        dao.getAllRecordEstimateCosts()

    fun getCostsLast12Months() =
        dao.getCostsLast12Months()
}