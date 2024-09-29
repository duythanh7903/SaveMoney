package com.example.baseprojectflamingo.ui.screen.manage_cost.actual

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baseprojectflamingo.ui.screen.manage_cost.actual.fragment.InputActualCostFragment
import com.example.baseprojectflamingo.ui.screen.manage_cost.actual.fragment.ListActualCostFragment

class ActualCostViewPager(
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> InputActualCostFragment()
            else -> ListActualCostFragment()
        }
}