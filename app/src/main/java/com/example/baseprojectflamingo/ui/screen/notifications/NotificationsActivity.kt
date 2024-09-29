package com.example.baseprojectflamingo.ui.screen.notifications

import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseActivity
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.base.ext.showToast
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.NotificationEntity
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import com.example.baseprojectflamingo.databinding.ActivityNotificationsBinding

class NotificationsActivity :
    BaseActivity<ActivityNotificationsBinding>(R.layout.activity_notifications) {

    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var viewModel: NotiViewModel

    override fun initView() {
        initViewModel()
        initRcv()
        observeData()
        clickViews()
    }

    private fun initViewModel() {
        val notiDao = AppDatabase.getInstance(this).notificationDao()
        val notiRepo = NotificationRepository(notiDao)
        val viewModelFactory = NotiViewModelFactory(notiRepo)
        viewModel = ViewModelProvider(this, viewModelFactory)[NotiViewModel::class.java]
    }

    private fun initRcv() = binding.rcv.apply {
        notificationAdapter = NotificationAdapter(this@NotificationsActivity)
        { noti, index ->
            if (noti.title == getString(R.string.notification_sample_1)) {
                showToast(R.string.this_is_sample_item)
            } else {
                if (!noti.isRead) viewModel.updateNoti(noti.apply {
                    isRead = true
                })
                notificationAdapter.notifyItemChanged(index)
            }
        }
        adapter = notificationAdapter
    }

    private fun observeData() = viewModel.apply {
        listNoti.observe(this@NotificationsActivity) { listNotification ->
            if (listNotification.isEmpty()) {
                val listTemp = mutableListOf(
                    NotificationEntity(
                        0L,
                        getString(R.string.notification_sample_1),
                        getString(R.string.content_sample_1),
                    )
                )
                notificationAdapter.submitData(listTemp)
            } else notificationAdapter.submitData(listNotification)
        }
    }

    private fun clickViews() = binding.apply {
        iconBack.click { finish() }

        textMarkAllDone.click { updateAllNoti() }
    }

    private fun updateAllNoti() {
        val listNotiIsNotRead =
            notificationAdapter.list.filter { !it.isRead }.toMutableList()
        listNotiIsNotRead.map { it.isRead = true }.toMutableList()
        viewModel.updateAllNoti(listNotiIsNotRead)
    }
}