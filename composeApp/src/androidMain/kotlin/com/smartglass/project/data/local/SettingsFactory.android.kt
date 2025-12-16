package com.smartglass.project.data.local

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

private var appContext: Context? = null

fun initAndroidSettings(context: Context) {
    appContext = context.applicationContext
}

actual fun createSettings(): Settings {
    val context = appContext ?: throw IllegalStateException("Android context not initialized. Call initAndroidSettings() first.")
    val sharedPreferences = context.getSharedPreferences("smart_glass_prefs", Context.MODE_PRIVATE)
    return SharedPreferencesSettings(sharedPreferences)
}
