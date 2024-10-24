package com.example.baseprojectflamingo.ui.screen.frg.news

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseRecyclerView
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.databinding.ItemNewsVerticalBinding

class NewVerticalAdapter(
    private val contextParams: Context,
    private val onShowDetail: (news: News) -> Unit
) : BaseRecyclerView<News>() {
    override fun getItemLayout(): Int = R.layout.item_news_vertical

    override fun setData(binding: ViewDataBinding, item: News, layoutPosition: Int) {
        if (binding is ItemNewsVerticalBinding) {
            Glide.with(contextParams).load(item.imageRes).into(binding.imagePost)
            Glide.with(contextParams).load(item.avatarAuthorRes).into(binding.iconAuthor)
            binding.textTime.text = item.createdAt
            binding.textCategory.text = item.category
            binding.textTitlePost.text = contextParams.getString(item.titleRes)
            binding.textAuthorName.text = item.authorName
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: News, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)
        if (binding is ItemNewsVerticalBinding) {
            binding.root.click { onShowDetail.invoke(obj) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<News>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }
}