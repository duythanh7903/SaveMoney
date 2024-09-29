package com.example.baseprojectflamingo.ui.screen.manage_income.real

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baseprojectflamingo.ui.screen.manage_income.real.frg.InputRealIncomeFragment
import com.example.baseprojectflamingo.ui.screen.manage_income.real.frg.ListRealInComeFragment

class RealIncomeViewPager(
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> InputRealIncomeFragment()
            else -> ListRealInComeFragment()
        }
}