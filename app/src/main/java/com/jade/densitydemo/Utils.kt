package com.jade.densitydemo

import android.content.Context
import kotlin.math.min


/**
 * 修改density
 */
fun Context.setDensity() {
    setDensityInternal()
}

private fun Context.setDensityInternal() {
    val resource = resources ?: return
    val metrics = resource.displayMetrics ?: return

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
    val configuration = resource.configuration ?: return
    configuration.screenWidthDp = (width / targetDensity)
    configuration.screenHeightDp = (height / targetDensity)
    configuration.densityDpi = targetDensityDpi

    // 修复dimen获取不对的问题
//    resource.updateConfiguration(configuration, metrics)
}

fun Context.getNavigationBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
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