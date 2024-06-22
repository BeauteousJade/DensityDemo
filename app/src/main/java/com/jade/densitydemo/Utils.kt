package com.jade.densitydemo

import android.content.Context
import android.content.res.Resources
import kotlin.math.min


/**
 * 修改density
 */
fun Context.setDensity() {
    resources?.setDensity()
}


fun Resources.setDensity() {
    val metrics = displayMetrics ?: return

    val width = metrics.widthPixels
    val height = metrics.heightPixels + getNavigationBarHeight()
    //获取以设计图总宽度360dp下的density值
    val targetDensity =
        (min(width, height) / 360f).toInt()
    //获取以设计图总宽度360dp下的dpi值
    val targetDensityDpi = (160f * targetDensity).toInt()

    // 更新displayMetrics的信息
    metrics.density = targetDensity.toFloat()
    metrics.scaledDensity = targetDensity.toFloat()
    metrics.densityDpi = targetDensityDpi

    // 更新configuration的信息
    val configuration = configuration ?: return
    configuration.screenWidthDp = (width / targetDensity)
    configuration.screenHeightDp = (height / targetDensity)
    configuration.densityDpi = targetDensityDpi

    // 修复dimen获取不对的问题
    updateConfiguration(configuration, metrics)
}

fun Context.getNavigationBarHeight(): Int {
    return resources?.getNavigationBarHeight() ?: 0
}


fun Resources.getNavigationBarHeight(): Int {
    var result = 0
    val resourceId = getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = getDimensionPixelSize(resourceId)
    }
    return result
}


fun Context.getScreenWidthDp(): Int {
    return resources?.displayMetrics?.let {
        (it.widthPixels / it.density + 0.5f).toInt()
    } ?: 0
}

fun Context.getDensity(): Float {
    return resources?.displayMetrics?.density ?: 0f
}


fun Int.pxToDp(context: Context): Int {
    return context.resources?.displayMetrics?.let {
        (this / it.density + 0.5f).toInt()
    } ?: 0
}