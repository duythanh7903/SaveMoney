package com.example.baseprojectflamingo.database.repository

import com.example.baseprojectflamingo.database.dao.RecordActualCostDao
import com.example.baseprojectflamingo.database.entities.RecordActualCost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActualCostRepository(
    private val dao: RecordActualCostDao
) {

    suspend fun saveRecordActualCost(record: RecordActualCost) =
        withContext(Dispatchers.IO) {
            dao.insertRecordActualCost(record)
        }

    fun getAllRecordActualCosts() =
        dao.getAllRecordActualCosts()

    fun getCostsLast12Months() =
        dao.getCostsLast12Months()
}