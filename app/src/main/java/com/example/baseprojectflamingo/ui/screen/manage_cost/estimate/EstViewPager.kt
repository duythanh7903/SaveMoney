package com.example.baseprojectflamingo.ui.screen.manage_cost.estimate

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baseprojectflamingo.ui.screen.manage_cost.estimate.frg.InputEstCostFragment
import com.example.baseprojectflamingo.ui.screen.manage_cost.estimate.frg.ListEstCostFragment

class EstViewPager(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> InputEstCostFragment()
            else -> ListEstCostFragment()
        }
}