package com.example.baseprojectflamingo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.baseprojectflamingo.database.entities.RecordRealIncome
import com.example.baseprojectflamingo.ui.domain.MonthlyMoney

@Dao
interface RecordRealIncomeDao {

    @Insert
    fun saveRecordRealIncome(record: RecordRealIncome)

    @Query("SELECT * FROM RECORD_REAL_INCOME ORDER BY time DESC")
    fun getAllRecordRealIncome(): LiveData<MutableList<RecordRealIncome>>

    @Query("""
        SELECT strftime('%Y-%m', datetime(time / 1000, 'unixepoch')) AS monthYear, 
               SUM(revenue) as totalCost 
        FROM RECORD_REAL_INCOME
        GROUP BY monthYear
        ORDER BY monthYear DESC
        LIMIT 12
    """)
    fun getRevenueLast12Months(): LiveData<MutableList<MonthlyMoney>>
}