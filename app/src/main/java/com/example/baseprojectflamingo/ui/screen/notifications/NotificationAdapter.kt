package com.example.baseprojectflamingo.ui.screen.notifications

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseRecyclerView
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.base.ext.convertLongToHHmm
import com.example.baseprojectflamingo.base.ext.hide
import com.example.baseprojectflamingo.base.ext.show
import com.example.baseprojectflamingo.database.entities.NotificationEntity
import com.example.baseprojectflamingo.databinding.ItemNotiBinding

class NotificationAdapter(
    private val contextParams: Context,
    private val onClickItem: (item: NotificationEntity, index: Int) -> Unit
) : BaseRecyclerView<NotificationEntity>() {
    override fun getItemLayout(): Int = R.layout.item_noti

    override fun setData(binding: ViewDataBinding, item: NotificationEntity, layoutPosition: Int) {
        if (binding is ItemNotiBinding) {
            binding.textTitle.text = item.title
            binding.textTitle.isSelected = true
            binding.textContent.text = item.content
            binding.textTime.text = contextParams.convertLongToHHmm(item.time)
            if (item.isRead) binding.iconWarningNotRead.hide() else binding.iconWarningNotRead.show()
        }
    }

    override fun onClickViews(
        binding: ViewDataBinding,
        obj: NotificationEntity,
        layoutPosition: Int
    ) {
        if (binding is ItemNotiBinding) {
            binding.root.click { onClickItem.invoke(obj, layoutPosition) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<NotificationEntity>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}