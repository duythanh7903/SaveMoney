package com.example.baseprojectflamingo.base

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.commons.PreferencesUtils
import java.util.Locale

abstract class BaseDialog<VB : ViewDataBinding>(
    context: Context,
    themeResId: Int = R.style.BaseDialog
) :
    Dialog(context, themeResId) {
    lateinit var binding: VB

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        createContentView()
        setLocale(context, PreferencesUtils.languageCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        onResizeViews()
        onClickViews()
    }

    private fun createContentView() {
        val layoutView = getLayoutDialog()
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutView, null, false)
        setContentView(binding.root)
    }

    abstract fun getLayoutDialog(): Int

    open fun initViews() {}

    open fun onResizeViews() {}

    open fun onClickViews() {}


    fun setDialogBottom() {
        window?.run {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.BOTTOM)
        }
    }

    fun setLocale(context: Context, language: String) {
        if (language.isEmpty()) {
            val config = Configuration()
            val locale = Locale.getDefault()
            Locale.setDefault(locale)
            config.locale = locale
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        } else {
            changeLang(language, context)
        }
    }

    private fun changeLang(lang: String, context: Context) {
        if (lang.equals("", ignoreCase = true)) return
        val myLocale = Locale(lang)
        Locale.setDefault(myLocale)
        val config = Configuration()
        config.locale = myLocale
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}