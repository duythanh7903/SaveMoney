package com.example.baseprojectflamingo.ui.screen.manage_income.real.frg

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseRecyclerView
import com.example.baseprojectflamingo.base.ext.convertLongToDateString2
import com.example.baseprojectflamingo.base.ext.formatNumberWithDots
import com.example.baseprojectflamingo.database.entities.RecordRealIncome
import com.example.baseprojectflamingo.databinding.ItemRecordRealIncomeBinding

class RealIncomeAdapter(
    private val contextParams: Context,
) : BaseRecyclerView<RecordRealIncome>() {
    override fun getItemLayout(): Int = R.layout.item_record_real_income

    @SuppressLint("SetTextI18n")
    override fun setData(binding: ViewDataBinding, item: RecordRealIncome, layoutPosition: Int) {
        if (binding is ItemRecordRealIncomeBinding) {
            Glide.with(contextParams).load(item.service.categoryParent.imageRes)
                .into(binding.iconCategory)
            binding.textMoney.text = "+ ${contextParams.formatNumberWithDots(item.revenue)}"
            binding.textTitle.text = item.noteTitle
            binding.textTime.text = contextParams.convertLongToDateString2(item.time)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<RecordRealIncome>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}