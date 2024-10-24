package com.example.baseprojectflamingo.ui.screen.frg.news

import android.content.Intent
import android.os.Bundle
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseFragment
import com.example.baseprojectflamingo.databinding.FragmentNewsBinding
import com.example.baseprojectflamingo.ui.screen.frg.news.NewsUtils.getNewsHorizontalList
import com.example.baseprojectflamingo.ui.screen.frg.news.NewsUtils.getNewsVerticalList

class NewsFragment : BaseFragment<FragmentNewsBinding>(R.layout.fragment_news) {

    private lateinit var newsHorizontalAdapter: NewHorizontalAdapter
    private lateinit var newsVerticalAdapter: NewVerticalAdapter

    override fun initView() {
        initNewsHorizontalAdapter()
        initNewsVerticalAdapter()
    }

    override fun initData() = Unit

    private fun initNewsHorizontalAdapter() = binding.rcvNewsTypeHorizontal.apply {
        newsHorizontalAdapter = NewHorizontalAdapter(requireActivity()) { news ->
            val intent = Intent(requireActivity(), NewsDetailActivity::class.java).apply {
                val bundle = Bundle().apply {
                    putSerializable("news_detail", news)
                }
                putExtras(bundle)
            }
            startActivity(intent)
        }.apply {
            submitData(getNewsHorizontalList())
        }

        adapter = newsHorizontalAdapter
    }

    private fun initNewsVerticalAdapter() = binding.rcvNewsTypeVertical.apply {
        newsVerticalAdapter = NewVerticalAdapter(requireActivity()){ news ->
            val intent = Intent(requireActivity(), NewsDetailActivity::class.java).apply {
                val bundle = Bundle().apply {
                    putSerializable("news_detail", news)
                }
                putExtras(bundle)
            }
            startActivity(intent)
        }.apply {
            submitData(getNewsVerticalList())
        }

        adapter = newsVerticalAdapter
    }
}