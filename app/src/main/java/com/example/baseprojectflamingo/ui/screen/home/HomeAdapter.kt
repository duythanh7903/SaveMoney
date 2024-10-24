package com.example.baseprojectflamingo.ui.screen.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baseprojectflamingo.ui.screen.frg.account.AccountFragment
import com.example.baseprojectflamingo.ui.screen.frg.chart.ChartFragment
import com.example.baseprojectflamingo.ui.screen.frg.home.HomeFragment
import com.example.baseprojectflamingo.ui.screen.frg.manage.ManageFragment
import com.example.baseprojectflamingo.ui.screen.frg.news.NewsFragment
import com.example.baseprojectflamingo.ui.screen.frg.plan.PlanFragment

class HomeAdapter(
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> HomeFragment()
            1 -> ChartFragment()
            2 -> ManageFragment()
            3 -> AccountFragment()
            4 -> NewsFragment()
            else -> HomeFragment()
        }
}