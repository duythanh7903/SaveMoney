package com.example.baseprojectflamingo.ui.screen.frg.manage

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseRecyclerView
import com.example.baseprojectflamingo.base.ext.convertLongToDateString2
import com.example.baseprojectflamingo.base.ext.formatNumberWithDots
import com.example.baseprojectflamingo.base.ext.hide
import com.example.baseprojectflamingo.base.ext.show
import com.example.baseprojectflamingo.databinding.ItemRecordManageBinding
import com.example.baseprojectflamingo.ui.domain.RecordManage
import com.example.baseprojectflamingo.ui.domain.RecordManage.Companion.RECORD_EXPECTED_INCOME
import com.example.baseprojectflamingo.ui.domain.RecordManage.Companion.RECORD_REAL_INCOME

class RecordManageAdapter(
    private val contextParams: Context
): BaseRecyclerView<RecordManage>() {
    override fun getItemLayout(): Int = R.layout.item_record_manage

    @SuppressLint("SetTextI18n")
    override fun setData(binding: ViewDataBinding, item: RecordManage, layoutPosition: Int) {
        if (binding is ItemRecordManageBinding) {
            if (item.typeRecord == RECORD_REAL_INCOME || item.typeRecord == RECORD_EXPECTED_INCOME) {
                binding.layoutRecordCost.hide()
                binding.layoutRecordIncome.show()
                Glide.with(contextParams).load(item.imageRes).into(binding.iconCategory)
                binding.textTitle.text = item.recordName
                binding.textTitle.isSelected = true
                binding.textTime.text = contextParams.convertLongToDateString2(item.time)
                binding.textMoney.text = "+ ${contextParams.formatNumberWithDots(item.amount)}"
            } else {
                binding.layoutRecordIncome.hide()
                binding.layoutRecordCost.show()
                Glide.with(contextParams).load(item.imageRes).into(binding.iconCategoryCost)
                binding.textTitleCost.text = item.recordName
                binding.textTitleCost.isSelected = true
                binding.textTimeCost.text = contextParams.convertLongToDateString2(item.time)
                binding.textMoneyCost.text = "- ${contextParams.formatNumberWithDots(item.amount)}"
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<RecordManage>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}