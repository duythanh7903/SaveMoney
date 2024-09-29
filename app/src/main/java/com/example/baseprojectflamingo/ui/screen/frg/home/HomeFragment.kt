package com.example.baseprojectflamingo.ui.screen.frg.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseFragment
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.base.ext.convertJsonToObject
import com.example.baseprojectflamingo.commons.PreferencesUtils
import com.example.baseprojectflamingo.database.AppDatabase
import com.example.baseprojectflamingo.database.entities.Activities
import com.example.baseprojectflamingo.database.entities.UserAccount
import com.example.baseprojectflamingo.database.repository.ActivitiesRepository
import com.example.baseprojectflamingo.database.repository.NotificationRepository
import com.example.baseprojectflamingo.databinding.FragmentHomeBinding
import com.example.baseprojectflamingo.ui.screen.language.LanguageActivity
import com.example.baseprojectflamingo.ui.screen.notifications.NotificationsActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel
    private lateinit var accountCurrent: UserAccount
    private lateinit var activitiesAdapter: ActivitiesAdapter

    override fun initData() = Unit

    override fun initView() {
        getAccountBalanceAndShowText()
        initRcvActivities()
        initViewModel()
        observeData()
        clickViews()
        viewModel.countNotiNotRead(binding)
    }

    private fun initViewModel() {
        val dao = AppDatabase.getInstance(getRootContext()).activitiesDao()
        val notiDao = AppDatabase.getInstance(getRootContext()).notificationDao()
        val repository = ActivitiesRepository(dao)
        val notiRepository = NotificationRepository(notiDao)
        val viewModelFactory = HomeViewModelFactory(repository, notiRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    private fun getAccountCurrent() = activity?.let { act ->
        val jsonAccountCurrent = PreferencesUtils.jsonAccount
        accountCurrent =
            act.convertJsonToObject(jsonAccountCurrent, UserAccount::class.java)
    }

    private fun showInformationUser() {
        if (this::accountCurrent.isInitialized) {
            val base64Avatar = accountCurrent.base64Image
            val userName = accountCurrent.userName

            Glide.with(getRootContext()).load(
                if (base64Avatar.isNotEmpty()) Uri.parse(base64Avatar)
                else R.drawable.avatar_default
            ).into(binding.avatarUser)
            binding.textUserName.text = userName
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getAccountBalanceAndShowText() {
        val accountBalance = PreferencesUtils.accountBalance
        binding.textAccountBalance.text = "$accountBalance VND"
    }

    private fun clickViews() = binding.apply {
        iconHideAccountBalance.click { eventHideAccountBalance() }

        iconNotification.click {
            startActivity(Intent(requireActivity(), NotificationsActivity::class.java))
        }

        iconSetting.click {
            startActivity(Intent(requireActivity(), LanguageActivity::class.java))
        }
    }

    private fun eventHideAccountBalance() {
        val isAlreadyHideAccBalance = viewModel.isAlreadyHideAccountBalance(binding)
        if (isAlreadyHideAccBalance) {
            viewModel.showAccountBalance(binding, PreferencesUtils.accountBalance)
        } else {
            viewModel.hideAccountBalance(binding)
        }
    }

    private fun getRootContext() = binding.root.context

    private fun observeData() = viewModel.apply {
        activities.observe(viewLifecycleOwner) { listActivities ->
            if (listActivities.isEmpty()) {
                val listSampleData = mutableListOf(
                    Activities(
                        0,
                        requireActivity().getString(R.string.this_is_sample_item),
                        System.currentTimeMillis(),
                        123456789,
                        false
                    )
                )
                activitiesAdapter.submitData(listSampleData)
            } else activitiesAdapter.submitData(listActivities)
        }
    }

    private fun initRcvActivities() = binding.rcvActivities.apply {
        activitiesAdapter = ActivitiesAdapter()

        adapter = activitiesAdapter
    }

    override fun onPause() {
        super.onPause()

        viewModel.hideAccountBalance(binding)
    }

    override fun onResume() {
        super.onResume()

        try {
            viewModel.countNotiNotRead(binding)
            getAccountCurrent()
            showInformationUser()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}