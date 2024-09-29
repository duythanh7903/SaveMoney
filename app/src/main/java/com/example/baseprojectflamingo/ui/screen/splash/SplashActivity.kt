package com.example.baseprojectflamingo.ui.screen.splash

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.CountDownTimer
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseActivity
import com.example.baseprojectflamingo.commons.Commons.getAllParentCategory
import com.example.baseprojectflamingo.commons.PreferencesUtils
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.repository.ParentCategoryRepository
import com.example.baseprojectflamingo.databinding.ActivitySplashBinding
import com.example.baseprojectflamingo.serivce.AlarmReceiver
import com.example.baseprojectflamingo.ui.screen.ob.OnBoardingActivity
import java.util.Calendar
import java.util.Locale

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private lateinit var viewModel: SplashViewModel

    override fun initView() {
        PreferencesUtils.init(this@SplashActivity)
        initViewModel()
        observeData()
        setLocale(this, PreferencesUtils.languageCode)
        startCountDownTimer(5000)
        setAlarm()
    }

    private fun startCountDownTimer(duration: Long) {
        val interval: Long = 100 // 0.1 giây
        object : CountDownTimer(duration, interval) {
            private var progress = 0

            override fun onTick(millisUntilFinished: Long) {
                progress += interval.toInt()
                binding.progressBar.progress = progress
            }

            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
                finish()
            }
        }.start()
    }

    private fun initViewModel() {
        val dao = AppDatabase.getInstance(this).parentCategoryDao()
        val repository = ParentCategoryRepository(dao)
        val viewModelFactory = ViewModelSplashFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SplashViewModel::class.java]
    }

    private fun observeData() = viewModel.apply {
        listCategoryParent.observe(this@SplashActivity)
        { categories ->
            if (categories.isEmpty()) {
                viewModel.insertAllCategory(getAllParentCategory())
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    fun setAlarm() {
        // Giả sử bạn muốn đặt lịch cho 10h sáng hôm nay
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 6)
            set(Calendar.MINUTE, 4)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            // Nếu thời gian đã qua, đặt cho ngày mai
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        // Tạo Intent để gửi đến AlarmReceiver
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Đặt lịch sử dụng AlarmManager
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}