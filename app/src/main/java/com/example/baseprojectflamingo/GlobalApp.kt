package com.example.baseprojectflamingo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.example.baseprojectflamingo.commons.AppConst.CHANEL_ID_NOTI
import com.example.baseprojectflamingo.commons.PreferencesUtils
import java.util.Locale

class GlobalApp: Application() {

    override fun onCreate() {
        super.onCreate()

        PreferencesUtils.init(this)
        createNotificationChannel(this)
        setLocale(this, PreferencesUtils.languageCode)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = CHANEL_ID_NOTI
            val channelName = getString(R.string.app_name)
            val channelDescription = "Channel Description"
            val channelImportance = NotificationManager.IMPORTANCE_DEFAULT

            val notificationChannel = NotificationChannel(channelId, channelName, channelImportance).apply {
                description = channelDescription
            }

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun setLocale(context: Context, language: String) {
        if (language.isEmpty()) {
            val config = Configuration()
            val locale = Locale.getDefault()
            Locale.setDefault(locale)
            config.locale = locale
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        } else {
            changeLang(language, context)
        }
    }

    private fun changeLang(lang: String, context: Context) {
        if (lang.equals("", ignoreCase = true)) return
        val myLocale = Locale(lang)
        Locale.setDefault(myLocale)
        val config = Configuration()
        config.locale = myLocale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

}