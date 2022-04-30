package com.ernestgm.myposition.configuration

import android.content.Context
import android.content.SharedPreferences

private const val ENABLED_TRACKING_INDEX = "config_enabled_tracking"

class PreferenceManager internal constructor(private val preferences: SharedPreferences) {

    fun setTrackingEnabled(enabled: Boolean) {
        preferences.edit().putBoolean(ENABLED_TRACKING_INDEX, enabled).apply()
    }

    fun getTrackingEnabled() : Boolean {
        return preferences.getBoolean(ENABLED_TRACKING_INDEX, true)
    }

    fun clearAll() {
        preferences.edit().clear().apply()
    }

    companion object {
        fun get(context: Context): PreferenceManager {
            return PreferenceManager(
                context.getSharedPreferences(
                    context.packageName,
                    Context.MODE_PRIVATE
                )
            )
        }
    }
}