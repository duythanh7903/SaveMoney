package com.example.baseprojectflamingo.ui.screen.frg.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseprojectflamingo.base.ext.formatNumberWithDots
import com.example.baseprojectflamingo.base.ext.hide
import com.example.baseprojectflamingo.base.ext.show
import com.example.baseprojectflamingo.database.repository.ActivitiesRepository
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import com.example.baseprojectflamingo.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    activityRepository: ActivitiesRepository,
    private val notiRepo: NotificationRepository
): ViewModel() {

    val activities = activityRepository.getAllActivities()

    @SuppressLint("SetTextI18n")
    fun hideAccountBalance(binding: FragmentHomeBinding) =
        binding.apply {
            textAccountBalance.text = "*** *** ***"
        }

    @SuppressLint("SetTextI18n")
    fun showAccountBalance(binding: FragmentHomeBinding, accBalance: Long) =
        binding.apply {
            textAccountBalance.text =
                "${binding.root.context.formatNumberWithDots(accBalance)} VND"
        }

    fun isAlreadyHideAccountBalance(binding: FragmentHomeBinding) =
        binding.textAccountBalance.text.toString().trim() == "*** *** ***"

    fun countNotiNotRead(binding: FragmentHomeBinding) =
        viewModelScope.launch(Dispatchers.IO) {
            val count = notiRepo.countNotificationNotRead()
            withContext(Dispatchers.Main) {
                binding.textCountNoti.text = count.toString()
                if (count != 0) binding.textCountNoti.show() else binding.textCountNoti.hide()
            }
        }

}