package com.example.baseprojectflamingo.base

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.getWindowInsetsController
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.util.Locale

abstract class BaseActivity<VB : ViewDataBinding>(@LayoutRes private val resId: Int) :
    AppCompatActivity() {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemNavigation(window)
        binding = DataBindingUtil.setContentView(this, resId)
        binding.lifecycleOwner = this
        initView()
    }

    abstract fun initView()

    private fun hideSystemNavigation(window: Window?) {
        if (window == null) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetController =
                getWindowInsetsController(window.decorView)
            if (windowInsetController != null) {
                windowInsetController.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                windowInsetController.hide(WindowInsetsCompat.Type.navigationBars())
                window.setDecorFitsSystemWindows(true)
            }
        } else {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideSystemNavigation(window)
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