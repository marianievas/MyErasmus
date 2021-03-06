package com.myerasmus.common



import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager {

    companion object Factory {
        private val appSettingsFile = "APP_SETTINGS"
        private fun getSharedPreferences(): SharedPreferences {
            return MyApp.getInstance().getSharedPreferences(
                appSettingsFile,
                Context.MODE_PRIVATE
            )
        }
        fun setStringValue(dataLabel: String, dataValue: String) {
            val editor = getSharedPreferences().edit()
            editor.putString(dataLabel, dataValue)
            editor.apply()
        }
        fun setBooleanValue(dataLabel: String, dataValue: Boolean) {
            val editor = getSharedPreferences().edit()
            editor.putBoolean(dataLabel, dataValue)
            editor.apply()
        }
        fun setIntValue(dataLabel: String, dataValue: Int) {
            val editor = getSharedPreferences().edit()
            editor.putInt(dataLabel, dataValue)
            editor.apply()
        }
        fun getStringValue(dataLabel: String): String? {
            return getSharedPreferences().getString(dataLabel, null)
        }
        fun getBooleanValue(dataLabel: String): Boolean {
            return getSharedPreferences().getBoolean(dataLabel, false)
        }
        fun getIntValue(dataLabel: String): Int {
            return getSharedPreferences().getInt(dataLabel, 0)
        }
        fun deleteData() {
            getSharedPreferences().edit().clear().apply()
        }
    }
}
