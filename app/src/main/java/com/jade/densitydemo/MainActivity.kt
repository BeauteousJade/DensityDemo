package com.jade.densitydemo

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
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

    private fun setupDensity() {
        setDensity()
        application.setDensity()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i("pby123", "onConfigurationChanged")
    }
}