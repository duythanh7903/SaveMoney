package com.example.baseprojectflamingo.database.repository

import com.example.baseprojectflamingo.database.dao.RecordRealIncomeDao
import com.example.baseprojectflamingo.database.entities.RecordRealIncome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealIncomeRepository(
    private val dao: RecordRealIncomeDao
) {

    suspend fun saveRecordRealIncome(record: RecordRealIncome) =
        withContext(Dispatchers.IO) {
            dao.saveRecordRealIncome(record)
        }

    fun getAllRecordsRealIncome() =
        dao.getAllRecordRealIncome()

    fun getCostsLast12Months() = dao.getRevenueLast12Months()

}