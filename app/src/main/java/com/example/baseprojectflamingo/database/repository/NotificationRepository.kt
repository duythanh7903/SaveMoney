package com.example.baseprojectflamingo.database.repository

import com.example.baseprojectflamingo.database.dao.NotificationDao
import com.example.baseprojectflamingo.database.entities.NotificationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationRepository(
    private val dao: NotificationDao
) {

    suspend fun saveNotification(notification: NotificationEntity) =
        withContext(Dispatchers.IO) {
            dao.saveNotification(notification)
        }

    suspend fun updateNotification(noti: NotificationEntity) =
        withContext(Dispatchers.IO) {
            dao.updateNotification(noti)
        }

    suspend fun updateAllNotifications(notis: MutableList<NotificationEntity>) =
        withContext(Dispatchers.IO) {
            dao.updateAllNotifications(notis)
        }

    fun getAllNotifications() =
        dao.getAllNotifications()

    fun countNotificationNotRead() =
        dao.countNotificationNotRead()

}