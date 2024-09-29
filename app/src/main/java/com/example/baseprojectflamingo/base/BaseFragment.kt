package com.example.baseprojectflamingo.base

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.baseprojectflamingo.commons.PreferencesUtils
import java.util.Locale

abstract class BaseFragment<VB : ViewDataBinding>(@LayoutRes private val resId: Int) : Fragment() {
    protected lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initView()
        setLocale(requireActivity(), PreferencesUtils.languageCode)
        return binding.root
    }

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    abstract fun initData()

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