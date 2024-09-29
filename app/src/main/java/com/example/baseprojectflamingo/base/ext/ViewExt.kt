package com.example.baseprojectflamingo.base.ext

import android.os.SystemClock
import android.view.View

private var lastClickTime: Long = 0

fun View.click(action: (view: View?) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < 300L) return
            else action(v)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}