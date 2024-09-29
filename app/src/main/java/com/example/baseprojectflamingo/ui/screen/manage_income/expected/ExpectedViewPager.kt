package com.example.baseprojectflamingo.ui.screen.manage_income.expected

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baseprojectflamingo.ui.screen.manage_income.expected.frg.InputExpectedIncomeFragment
import com.example.baseprojectflamingo.ui.screen.manage_income.expected.frg.ListExpectedIncomeFragment

class ExpectedViewPager(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> InputExpectedIncomeFragment()
        else -> ListExpectedIncomeFragment()
    }
}