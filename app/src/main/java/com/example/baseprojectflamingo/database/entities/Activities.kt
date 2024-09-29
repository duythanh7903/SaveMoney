package com.example.baseprojectflamingo.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ACTIVITIES")
data class Activities(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var eventName: String = "",
    var createdAt: Long = System.currentTimeMillis(),
    var balanceFluctuations: Long = 0,
    var isBalanceVolatilityIncreasing: Boolean = false,
    var balanceAmount: Long = 0L
) {
}