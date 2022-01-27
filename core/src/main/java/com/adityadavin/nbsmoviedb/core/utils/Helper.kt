package com.adityadavin.nbsmoviedb.core.utils

import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowCompat

fun Int.runtimeFormat(): String {
    return if (this > 60) {
        val hours = this / 60
        val minutes = this % 60
        "${hours}h ${minutes}m"
    } else {
        "${this}m"
    }
}


@Suppress("DEPRECATION")
fun Activity.fullScreen() {
    window.statusBarColor = Color.TRANSPARENT
    if (Build.VERSION.SDK_INT in 21..29) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility =
            SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or SYSTEM_UI_FLAG_LAYOUT_STABLE

    } else if (Build.VERSION.SDK_INT >= 30) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}

fun View.setMargin(
    marginLeft: Int? = null,
    marginTop: Int? = null,
    marginRight: Int? = null,
    marginBottom: Int? = null,
) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(
        marginLeft ?: 0,
        marginTop ?: 0,
        marginRight ?: 0,
        marginBottom ?: 0
    )
    this.layoutParams = menuLayoutParams
}

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )