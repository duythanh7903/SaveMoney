package com.example.baseprojectflamingo.commons

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtils {

    private const val SHARED_PREFERENCES_NAME = "TEXT_REPEATER"


    private var sharePref: SharedPreferences? = null
    fun init(context: Context) {
        if (sharePref == null) {
            sharePref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        }
    }

    fun setValue(keyWord: String, value: Any?) {
        val editor = sharePref?.edit()
        when (value) {
            is Int -> editor?.putInt(keyWord, value)
            is Float -> editor?.putFloat(keyWord, value)
            is Long -> editor?.putLong(keyWord, value)
            is Boolean -> editor?.putBoolean(keyWord, value)
            is String -> editor?.putString(keyWord, value)
        }
        editor?.apply()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(keyWord: String, defaultValue: T): T {
        return when (defaultValue) {
            is Int -> (sharePref?.getInt(keyWord, defaultValue) ?: defaultValue) as T
            is Long -> (sharePref?.getLong(keyWord, defaultValue) ?: defaultValue) as T
            is Float -> (sharePref?.getFloat(keyWord, defaultValue) ?: defaultValue) as T
            is Boolean -> (sharePref?.getBoolean(keyWord, defaultValue) ?: defaultValue) as T
            is String -> (sharePref?.getString(keyWord, defaultValue) ?: defaultValue) as T
            else -> return defaultValue
        }
    }

    var languageCode: String
        get() = getValue("language_code", "vi")
        set(value) = setValue("language_code", value)

    var jsonAccount: String
        get() = getValue("json_account", "")
        set(value) = setValue("json_account", value)

    var accountBalance: Long
        get() = getValue("account_balance", 0)
        set(value) = setValue("account_balance", value)

    var filterType: Int
        get() = getValue("filter_type", 0)
        set(value) = setValue("filter_type", value)
}