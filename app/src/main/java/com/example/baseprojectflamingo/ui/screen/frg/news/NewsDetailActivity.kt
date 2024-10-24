package com.example.baseprojectflamingo.ui.screen.frg.news

import com.bumptech.glide.Glide
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseActivity
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : BaseActivity<ActivityNewsDetailBinding>(R.layout.activity_news_detail) {
    override fun initView() {
        val bundle = intent.extras
        bundle?.let {
            val news = it.getSerializable("news_detail") as News
            binding.textTitle.text = getString(news.titleRes)
            binding.textAuthorName.text = news.authorName
            binding.textContent.text = getString(news.contentRes)
            binding.textTime.text = news.createdAt
            binding.textCategory.text = news.category
            Glide.with(this).load(news.imageRes).into(binding.imgPost)
            Glide.with(this).load(news.avatarAuthorRes).into(binding.iconAuthor)
        }

        binding.iconBack.click { finish() }
    }

}