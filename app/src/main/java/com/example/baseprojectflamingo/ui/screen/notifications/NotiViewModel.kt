package com.example.baseprojectflamingo.ui.screen.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojectflamingo.database.entities.NotificationEntity
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class NotiViewModel(
    private val notiRepo: NotificationRepository
): ViewModel() {

    val listNoti =
        notiRepo.getAllNotifications()

    fun updateNoti(noti: NotificationEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            notiRepo.updateNotification(noti)
            cancel()
        }

    fun updateAllNoti(notis: MutableList<NotificationEntity>) =
        viewModelScope.launch(Dispatchers.IO) {
            notiRepo.updateAllNotifications(notis)
            cancel()
        }

}