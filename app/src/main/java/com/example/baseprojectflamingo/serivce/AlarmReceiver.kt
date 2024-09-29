package com.example.baseprojectflamingo.serivce

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.NotificationEntity
import com.example.baseprojectflamingo.ui.screen.splash.SplashActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        sendNotification(context, intent)
    }

    private fun sendNotification(context: Context, intent: Intent) {
        val channelId = "my_channel_id"
        val notificationId = 1
        val contentTitle = intent.getStringExtra("content_title") ?: "Remind"
        val contentText = intent.getStringExtra("content_text") ?: "It's time to spend the money on the plan you made in advance!"
        val descriptionContentText = intent.getStringExtra("description_content_text") ?: ""
        val textContent = "$contentText\n$descriptionContentText"

        // Tạo Notification Channel (Android 8.0 trở lên)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "My Channel", NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }

        // Tạo Intent cho Notification
        val notificationIntent = Intent(context, SplashActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Tạo Notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_noti) // Thay thế với icon của bạn
            .setContentTitle(contentTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // Gửi Notification
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager?.notify(notificationId, notification)

        // save to sql
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstance(context).notificationDao().saveNotification(
                NotificationEntity(
                    title = "Remind",
                    content = textContent,
                )
            )
        }
    }
}