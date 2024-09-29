package com.example.baseprojectflamingo.ui.screen.language

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.base.BaseActivity
import com.example.baseprojectflamingo.base.ext.click
import com.example.baseprojectflamingo.commons.Commons.getAllLanguages
import com.example.baseprojectflamingo.commons.PreferencesUtils
import com.example.baseprojectflamingo.databinding.ActivityLanguageBinding
import com.example.baseprojectflamingo.ui.screen.splash.SplashActivity
import java.util.Locale

class LanguageActivity : BaseActivity<ActivityLanguageBinding>(R.layout.activity_language) {

    private lateinit var languageAdapter: LanguageAdapter

    override fun initView() {
        initRcv()
        clickViews()
    }

    private fun initRcv() = binding.rcv.apply {
        var indexSelectedCache = 0
        for (i in 0 until getAllLanguages().size) {
            if (getAllLanguages()[i].code == PreferencesUtils.languageCode) {
                indexSelectedCache = i
                break
            }
        }
        languageAdapter = LanguageAdapter(this@LanguageActivity)
        { language, index ->
            setLocale(this@LanguageActivity, language.code)
            PreferencesUtils.languageCode = language.code
            val indexSelectedBefore =  languageAdapter.indexSelected
            languageAdapter.indexSelected = index
            languageAdapter.notifyItemChanged(indexSelectedBefore)

            startActivity(Intent(this@LanguageActivity, SplashActivity::class.java))
            finish()
        }.apply {
            indexSelected = indexSelectedCache
            submitData(getAllLanguages())
        }

        adapter = languageAdapter
    }

    private fun clickViews() {
        binding.iconBack.click { finish() }
    }
}