package com.example.flutter_application_1

import android.os.Build
import android.view.Display
import io.flutter.embedding.android.FlutterActivity

class MainActivity : FlutterActivity() {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        requestHighRefreshRate()
    }

    override fun onResume() {
        super.onResume()
        requestHighRefreshRate()
    }

    private fun requestHighRefreshRate() {
        val currentDisplay = currentDisplay() ?: return
        val highestMode = currentDisplay.supportedModes.maxWithOrNull(
            compareBy<Display.Mode>({ it.refreshRate }, { it.physicalWidth * it.physicalHeight }),
        ) ?: return

        val params = window.attributes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            params.preferredDisplayModeId = highestMode.modeId
        }
        params.preferredRefreshRate = highestMode.refreshRate
        window.attributes = params
    }

    private fun currentDisplay(): Display? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display
        } else {
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay
        }
    }
}
