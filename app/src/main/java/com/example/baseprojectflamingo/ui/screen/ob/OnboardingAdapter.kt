package com.example.baseprojectflamingo.ui.screen.ob

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseRecyclerView
import com.example.baseprojectflamingo.databinding.ItemOnBoardingBinding
import com.example.baseprojectflamingo.ui.domain.OnBoarding

class OnboardingAdapter : BaseRecyclerView<OnBoarding>() {
    override fun getItemLayout(): Int = R.layout.item_on_boarding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun setData(binding: ViewDataBinding, item: OnBoarding, layoutPosition: Int) {
        if (binding is ItemOnBoardingBinding) {
            binding.textTitleOnboarding.text = context?.getString(item.titleRes) ?: ""
            binding.textDescriptionOnboarding.text = context?.getString(item.descriptionRes) ?: ""
            binding.imgGuide.setImageResource(item.imageRes)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<OnBoarding>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }
}