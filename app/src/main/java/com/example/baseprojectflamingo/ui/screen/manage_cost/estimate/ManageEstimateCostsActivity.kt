package com.example.baseprojectflamingo.ui.screen.manage_cost.estimate

import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseActivity
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.databinding.ActivityManageEstimateCostsBinding

class ManageEstimateCostsActivity :
    BaseActivity<ActivityManageEstimateCostsBinding>(R.layout.activity_manage_estimate_costs) {

    private lateinit var vpgAdapter: EstViewPager

    override fun initView() {
        initVpg()
        initUiTopTab()
        clickViews()
    }

    private fun initVpg() = binding.layoutContainer.apply {
        vpgAdapter = EstViewPager(this@ManageEstimateCostsActivity)

        adapter = vpgAdapter
        isUserInputEnabled = false
    }

    private fun clickViews() = binding.apply {
        textTab1.click { changeTab1() }
        textTab2.click { changeTab2() }
    }

    private fun changeTab1() = binding.apply {
        textTab1.setTextColor(getColor(R.color.text_orange))
        textTab2.setTextColor(getColor(R.color.text_gray))
        textTab1.isActivated = true
        textTab2.isActivated = false
        layoutContainer.currentItem = 0
    }

    private fun changeTab2() = binding.apply {
        textTab2.setTextColor(getColor(R.color.text_orange))
        textTab1.setTextColor(getColor(R.color.text_gray))
        textTab1.isActivated = false
        textTab2.isActivated = true
        layoutContainer.currentItem = 1
    }

    private fun initUiTopTab() = binding.apply {
        textTab1.isActivated = true
        textTab2.isActivated = false
    }
}