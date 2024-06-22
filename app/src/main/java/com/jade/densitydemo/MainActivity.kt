package com.jade.densitydemo

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setupDensity()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.button).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val value = resources.getDimensionPixelOffset(R.dimen.width)
                val typedValue = TypedValue()
                resources.getValue(R.dimen.width, typedValue, true)
                val rawValue = typedValue.data
                Log.i(
                    "pby123",
                    "rawValue to 2:${Integer.toBinaryString(rawValue)}, value to 2:${
                        Integer.toBinaryString(value)
                    } "
                )
                Log.i(
                    "pby123",
                    "value:${value}px, ${value.pxToDp(this@MainActivity)}dp, screenWidthDp:${getScreenWidthDp()}"
                )
            }
        })

        // 因为声明为sensorLandscape，在方向发生变化，不会回调onConfigurationChanged，所以在这里去监听方向的变化。
        findViewById<View>(R.id.container).apply {
            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                private var mLastRotation = -1
                override fun onGlobalLayout() {
                    if (display.rotation != mLastRotation) {
                        mLastRotation = display.rotation
                        setupDensity()
                    }
                }

            })
        }
    }

    override fun getResources(): Resources {
        val resources = super.getResources()
        resources.setDensity()
        return resources
    }

    private fun setupDensity() {
        setDensity()
        application.setDensity()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i("pby123", "onConfigurationChanged")
    }
}