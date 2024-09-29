package com.example.baseprojectflamingo.ui.screen.ob

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseActivity
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.databinding.ActivityOnboardingBinding
import com.example.baseprojectflamingo.ui.screen.login.LoginActivity
import kotlin.math.abs

class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {

    private lateinit var onBoardingAdapter: OnboardingAdapter
    private lateinit var compositePageTransformer: CompositePageTransformer
    private lateinit var listIndicatorView: MutableList<ImageView>

    private var posViewPager = 0

    private val viewModel: OnBoardingViewModel by viewModels()

    override fun initView() {
        initVpg()
        fetchData()
        observeData()
        clickViews()
    }

    private fun initVpg() = binding.vpg2.apply {
        onBoardingAdapter = OnboardingAdapter()
        compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(100))
            addTransformer { view, position ->
                val r = 1 - abs(position)
                view.scaleY = 0.8f + r * 0.2f
                val absPosition = abs(position)
                view.alpha = 1.0f - (1.0f - 0.3f) * absPosition
            }
        }
        listIndicatorView = mutableListOf<ImageView>().apply {
            add(binding.indicator1)
            add(binding.indicator2)
            add(binding.indicator3)
        }

        adapter = onBoardingAdapter
        clipToPadding = false
        clipChildren = false
        offscreenPageLimit = 3
        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_ALWAYS
        setPageTransformer(compositePageTransformer)
        registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            @SuppressLint("InvalidAnalyticsName", "UseCompatLoadingForDrawables")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                posViewPager = position
                viewModel.updateIndicatorView(
                    indexSelected = position,
                    indicators = listIndicatorView
                )
            }
        })
    }

    private fun fetchData() {
        viewModel.fetchDataIntro()
    }

    private fun observeData() = viewModel.apply {
        introList.observe(this@OnBoardingActivity) { listIntro ->
            onBoardingAdapter.submitData(listIntro)
        }
    }

    private fun clickViews() = binding.apply {
        textNext.click {
            if (vpg2.currentItem < 2) vpg2.currentItem++ else gotoNextScreen()
        }

        textSkip.click { gotoNextScreen() }
    }

    private fun gotoNextScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}