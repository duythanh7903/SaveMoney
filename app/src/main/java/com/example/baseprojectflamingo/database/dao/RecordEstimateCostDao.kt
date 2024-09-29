package com.example.baseprojectflamingo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.baseprojectflamingo.database.entities.RecordEstimateCost
import com.example.baseprojectflamingo.ui.domain.MonthlyMoney

@Dao
interface RecordEstimateCostDao {

    @Insert
    fun insertRecordEstimateCost(recordEstimateCost: RecordEstimateCost)

    @Query("SELECT * FROM RECORD_ESTIMATE_COSTS ORDER BY time DESC")
    fun getAllRecordEstimateCosts(): LiveData<MutableList<RecordEstimateCost>>

    @Query("""
        SELECT strftime('%Y-%m', datetime(time / 1000, 'unixepoch')) AS monthYear, 
               SUM(cost) as totalCost 
        FROM RECORD_ESTIMATE_COSTS
        GROUP BY monthYear
        ORDER BY monthYear DESC
        LIMIT 12
    """)
    fun getCostsLast12Months(): LiveData<MutableList<MonthlyMoney>>

}