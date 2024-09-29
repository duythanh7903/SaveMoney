package com.example.baseprojectflamingo.ui.screen.language

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseRecyclerView
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.databinding.ItemLanguageBinding
import com.example.baseprojectflamingo.ui.domain.Language

class LanguageAdapter(
    private val contextParams: Context,
    private val onItemClick: (language: Language, index: Int) -> Unit,
) : BaseRecyclerView<Language>() {

    var indexSelected: Int = -1
        set(value) {
            field = value
            notifyItemChanged(indexSelected)
        }

    override fun getItemLayout(): Int = R.layout.item_language

    override fun setData(binding: ViewDataBinding, item: Language, layoutPosition: Int) {
        if (binding is ItemLanguageBinding) {
            binding.textCountryName.text =
                contextParams.getString(item.countryNameRes)
            Glide.with(contextParams).load(item.countriesFlag).into(binding.iconFlag)
            binding.iconTick.isActivated = indexSelected == layoutPosition
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: Language, layoutPosition: Int) {
        if (binding is ItemLanguageBinding) {
            binding.root.click {
                onItemClick.invoke(obj, layoutPosition)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<Language>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}