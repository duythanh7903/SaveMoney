package com.example.baseprojectflamingo.database.repository

import com.example.baseprojectflamingo.database.dao.RecordExpectedIncomeDao
import com.example.baseprojectflamingo.database.entities.RecordExpectedIncome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExpectedIncomeRepository(
    private val dao: RecordExpectedIncomeDao
) {

    suspend fun saveRecordExpectedIncome(record: RecordExpectedIncome) =
        withContext(Dispatchers.IO) {
            dao.saveRecordExpectedIncome(record)
        }

    fun getAllRecordExpectedIncome() =
        dao.getAllRecordExpectedIncome()

    fun getRevenueLast12Months() =
        dao.getRevenueLast12Months()

}