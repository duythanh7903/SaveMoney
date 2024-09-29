package com.example.baseprojectflamingo.base.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.example.baseprojectflamingo.base.custom.SafeOnClickListener
import com.example.baseprojectflamingo.commons.PermissionUtils
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

var toast: Toast? = null
fun Context.showToast(message: String) {
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast!!.show()
}

fun Context.showToast(message: Int) {
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast!!.show()
}

@SuppressLint("ClickableViewAccessibility")
private fun View.setOnClickAffect(scaleDiff: Float = 0.015f, listener: View.OnClickListener) {
    this.setOnTouchListener { v, motionEvent ->
        when (motionEvent?.action) {
            MotionEvent.ACTION_DOWN -> {
                v?.scaleX = 1 - scaleDiff
                v?.scaleY = 1 - scaleDiff
            }

            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP -> {
                v?.scaleX = 1f
                v?.scaleY = 1f
            }
        }
        false
    }
    this.setOnClickListener(listener)
}

fun View.setClickAffect(onClick: ((View?) -> Unit)) {
    val listener = SafeOnClickListener()
    listener.onSafeClick = onClick
    this.setOnClickAffect(0.015f, listener)
}

fun View.setClickAffect2(onClick: ((View?) -> Unit)) {
    val listener = SafeOnClickListener()
    listener.onSafeClick = onClick
    this.setOnClickAffect(0.04f, listener)
}

fun View.setClickListener(onClick: ((View?) -> Unit)? = null) {
    val listener = SafeOnClickListener()
    listener.onSafeClick = onClick
    this.setOnClickListener(listener)
}

fun Activity.hideKeyboard() {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}

fun Activity.showKeyboard(editText: View) {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
}

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View?.hide() {
    this?.visibility = View.GONE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun Context.hasNetworkConnection(): Boolean {
    var haveConnectedWifi = false
    var haveConnectedMobile = false
    val cm =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.allNetworkInfo
    for (ni in netInfo) {
        if (ni.typeName.equals("WIFI", ignoreCase = true))
            if (ni.isConnected) haveConnectedWifi = true
        if (ni.typeName.equals("MOBILE", ignoreCase = true))
            if (ni.isConnected) haveConnectedMobile = true
    }
    return haveConnectedWifi || haveConnectedMobile
}

fun Window.hideSystemNavigationBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = WindowCompat.getInsetsController(this, this.decorView)
        controller.hide(WindowInsetsCompat.Type.navigationBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    } else {
        this.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }
}

fun Context.validateEmail(email: String): Boolean {
    val emailPattern =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    val pattern = Pattern.compile(emailPattern)

    return pattern.matcher(email).matches()
}

fun Context.validatePassword(password: String): Boolean = password.length >= 8

fun <T> Context.convertObjectToJson(obj: T): String = Gson().toJson(obj)

fun <T> Context.convertJsonToObject(json: String, classOfT: Class<T>): T =
    Gson().fromJson(json, classOfT)

fun Context.convertLongToDateString(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("MMM    dd, yyyy", Locale.getDefault())
    return format.format(date)
}

fun Context.convertLongToDateString2(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("HH:mm, dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}

fun Context.convertLongToDateString3(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}

fun Context.formatNumberWithDots(number: Long): String {
    return "%,d".format(number).replace(",", ".")
}

fun Context.convertDateStringToLong(dateString: String): Long {
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.parse(dateString)?.time ?: 0L
}

fun Context.convertLongToHHmm(timeInMillis: Long): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = Date(timeInMillis)
    return dateFormat.format(date)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Context.isReadMediaImageAccepted() = PermissionUtils.isReadMediaImageAccepted(this)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Context.requestReadMediaImagePermission(fragment: Fragment, callback: () -> Unit) =
    PermissionUtils.requestReadMediaImagePermission(fragment, callback)

fun Context.isReadExternalStorageAccepted() = PermissionUtils.isReadExternalStorageAccepted(this)

fun Context.requestReadExternalStoragePermission(fragment: Fragment, callback: () -> Unit) =
    PermissionUtils.requestReadExternalPermission(fragment, callback)