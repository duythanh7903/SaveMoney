package com.example.baseprojectflamingo.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseRecyclerView
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.database.entities.ParentCategory
import com.example.baseprojectflamingo.databinding.ItemCategoryParentBinding

class ParentCategoryAdapter(
    private val contextParams: Context,
    private val onClickItem: (category: ParentCategory, index: Int) -> Unit
) : BaseRecyclerView<ParentCategory>() {

    var indexSelected: Int = 0
        set(value) {
            field = value
            notifyItemChanged(indexSelected)
        }

    override fun getItemLayout(): Int = R.layout.item_category_parent

    override fun setData(binding: ViewDataBinding, item: ParentCategory, layoutPosition: Int) {
        if (binding is ItemCategoryParentBinding) {
            Glide.with(contextParams).load(item.imageRes).into(binding.iconCategoryParent)
            binding.textCategoryName.text = item.categoryName
            binding.layoutContainer.isActivated = layoutPosition == indexSelected
            binding.textCategoryName.setTextColor(
                contextParams.getColor(
                    if (layoutPosition == indexSelected) R.color.white
                    else R.color.text_dark_blue
                )
            )
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: ParentCategory, layoutPosition: Int) {
        if (binding is ItemCategoryParentBinding) {
            binding.root.click { onClickItem.invoke(obj, layoutPosition) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<ParentCategory>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}