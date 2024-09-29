package com.example.baseprojectflamingo.ui.screen.home

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseActivity
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.databinding.ActivityHomeBinding
import com.tbruyelle.rxpermissions3.RxPermissions

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var listIconTab: MutableList<ImageView>
    private lateinit var listTextTab: MutableList<TextView>

    override fun initView() {
        initHomeAdapter()
        initListUi()
        initFirstTab()
        requestNotificationPermissions()
        requestSchedulePermissions()
    }

    private fun initHomeAdapter() = binding.layoutContainer.apply {
        homeAdapter = HomeAdapter(this@HomeActivity)

        adapter = homeAdapter
        isUserInputEnabled = false
        offscreenPageLimit = 5
    }

    private fun initListUi() {
        initListIconTab()
        initTextTab()
        clickViews()
    }

    private fun initListIconTab() {
        listIconTab = mutableListOf(
            binding.iconTab1,
            binding.iconTab2,
            binding.iconTab3,
            binding.iconTab4
        )
    }

    private fun initTextTab() {
        listTextTab = mutableListOf(
            binding.textTab1,
            binding.textTab2,
            binding.textTab3,
            binding.textTab4,
        )
    }

    private fun clickViews() = binding.apply {
        buttonTab1.click { onEventChangeTab(0) }
        buttonTab2.click { onEventChangeTab(1) }
        buttonTab3.click { onEventChangeTab(2) }
        buttonTab4.click { onEventChangeTab(3) }
    }

    private fun onEventChangeTab(indexTab: Int) {
        changeUi(indexTab)
        changeTab(indexTab)
    }

    private fun changeUi(indexTab: Int) {
        val typeFaceUnActive = ResourcesCompat.getFont(this, R.font.font_regular)
        val typeFaceActive = ResourcesCompat.getFont(this, R.font.font_semi_bold)
        val textColorActive = getColor(R.color.colorAds)
        val textColorUnActive = getColor(R.color.text_gray)

        listTextTab.forEach { text ->
            text.apply {
                typeface = typeFaceUnActive
                setTextColor(textColorUnActive)
            }
        }
        listIconTab.forEach { icon ->
            icon.setColorFilter(textColorUnActive, PorterDuff.Mode.SRC_IN)
        }

        listTextTab[indexTab].apply {
            typeface = typeFaceActive
            setTextColor(textColorActive)
        }
        listIconTab[indexTab].setColorFilter(textColorActive, PorterDuff.Mode.SRC_IN)
    }

    private fun changeTab(indexTab: Int) {
        binding.layoutContainer.currentItem = indexTab
    }

    private fun initFirstTab() = onEventChangeTab(0)

    @SuppressLint("CheckResult")
    private fun requestNotificationPermissions() {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED
        ) {
            RxPermissions(this).requestEach(android.Manifest.permission.POST_NOTIFICATIONS)
                .subscribe { permission ->
                }
        }
    }

    @SuppressLint("CheckResult")
    private fun requestSchedulePermissions() {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            checkSelfPermission(android.Manifest.permission.SCHEDULE_EXACT_ALARM) == PackageManager.PERMISSION_DENIED
        ) {
            RxPermissions(this).requestEach(android.Manifest.permission.SCHEDULE_EXACT_ALARM)
                .subscribe { permission ->

                }
        }
    }
}