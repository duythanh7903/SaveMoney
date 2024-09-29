package com.example.baseprojectflamingo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.baseprojectflamingo.database.entities.RecordExpectedIncome
import com.example.baseprojectflamingo.ui.domain.MonthlyMoney

@Dao
interface RecordExpectedIncomeDao {

    @Insert
    fun saveRecordExpectedIncome(record: RecordExpectedIncome)

    @Query("SELECT * FROM RECORD_EXPECTED_INCOME ORDER BY time DESC")
    fun getAllRecordExpectedIncome(): LiveData<MutableList<RecordExpectedIncome>>

    @Query("""
        SELECT strftime('%Y-%m', datetime(time / 1000, 'unixepoch')) AS monthYear, 
               SUM(revenue) as totalCost 
        FROM RECORD_EXPECTED_INCOME
        GROUP BY monthYear
        ORDER BY monthYear DESC
        LIMIT 12
    """)
    fun getRevenueLast12Months(): LiveData<MutableList<MonthlyMoney>>
}