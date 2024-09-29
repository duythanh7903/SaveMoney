package com.example.baseprojectflamingo.ui.screen.frg.home

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseRecyclerView
import com.example.baseprojectflamingo.base.ext.convertLongToDateString
import com.example.baseprojectflamingo.base.ext.formatNumberWithDots
import com.example.baseprojectflamingo.database.entities.Activities
import com.example.baseprojectflamingo.databinding.ItemActivitiesBinding

class ActivitiesAdapter : BaseRecyclerView<Activities>() {
    override fun getItemLayout(): Int = R.layout.item_activities

    @SuppressLint("SetTextI18n")
    override fun setData(binding: ViewDataBinding, item: Activities, layoutPosition: Int) {
        if (binding is ItemActivitiesBinding) {
            val isRise = item.isBalanceVolatilityIncreasing
            if (isRise) {
                binding.textBalanceFluctuations.text =
                    "+ ${context?.formatNumberWithDots(item.balanceFluctuations.toLong()) ?: item.balanceFluctuations}"
                binding.iconWarning.setImageResource(R.drawable.ic_up)
                binding.iconWarning.setBackgroundResource(R.drawable.bg_circle_green)
            } else {
                binding.textBalanceFluctuations.text =
                    "- ${context?.formatNumberWithDots(item.balanceFluctuations.toLong()) ?: item.balanceFluctuations}"
                binding.iconWarning.setImageResource(R.drawable.ic_down)
                binding.iconWarning.setBackgroundResource(R.drawable.bg_circle_red)
            }
            binding.textEventName.isSelected = true
            binding.textEventName.text = item.eventName
            binding.textDateTime.text =
                context?.convertLongToDateString(item.createdAt) ?: "Apr 22, 2024"
            binding.textAmount.text = "${binding.root.context.getString(R.string.amount_params, binding.root.context.formatNumberWithDots(item.balanceAmount))}VND"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<Activities>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}