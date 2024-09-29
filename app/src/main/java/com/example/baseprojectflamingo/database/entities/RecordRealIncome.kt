package com.example.baseprojectflamingo.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RECORD_REAL_INCOME")
data class RecordRealIncome(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var noteTitle: String = "",
    var revenue: Long = 0,
    var time: Long = System.currentTimeMillis(),
    var service: ChildCategory = ChildCategory()
) {
}