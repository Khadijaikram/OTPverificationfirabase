package com.my.khataapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharePrefnce(context: Context) {
    private val MY_PREF = "MyPreferences"
    var sharedPreferences: SharedPreferences = context.getSharedPreferences(MY_PREF, 0)
    private var editor: SharedPreferences.Editor? = sharedPreferences.edit()

    fun setString(key: String?, value: String?) {
        editor!!.putString(key, value)
        editor!!.apply()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }

    fun setBool(key: String?, value: Boolean) {
        editor!!.putBoolean(key, value)
        editor!!.apply()
    }

    fun getBool(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun setInt(key: String?, value: Int) {
        editor!!.putInt(key, value)
        editor!!.apply()
    }

    fun getInt(key: String?): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun clear(key: String?) {
        editor!!.remove(key)
        editor!!.apply()
    }

    fun clear() {
        editor!!.clear()
        editor!!.apply()
    }
}