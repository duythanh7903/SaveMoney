package com.example.baseprojectflamingo.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("NOTIFICATION_ENTITY")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var title: String = "",
    var content: String = "",
    var time: Long = System.currentTimeMillis(),
    var isRead: Boolean = false
) {
}