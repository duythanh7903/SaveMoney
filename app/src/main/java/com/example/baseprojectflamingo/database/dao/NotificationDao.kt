package com.example.baseprojectflamingo.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.baseprojectflamingo.database.entities.NotificationEntity

@Dao
interface NotificationDao {

    @Insert
    fun saveNotification(notification: NotificationEntity)

    @Query("SELECT * FROM NOTIFICATION_ENTITY ORDER BY time DESC")
    fun getAllNotifications(): LiveData<MutableList<NotificationEntity>>

    @Update
    fun updateNotification(notification: NotificationEntity)

    @Update
    fun updateAllNotifications(notifications: MutableList<NotificationEntity>)

    @Query("SELECT COUNT(id) FROM NOTIFICATION_ENTITY WHERE isRead == 0")
    fun countNotificationNotRead(): Int

}