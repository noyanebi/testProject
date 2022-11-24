package com.test.data.local.pref

import android.content.Context
import android.content.SharedPreferences

/**
 * CREATED BY Javadroid FOR `WiCalory` PROJECT
 * AT: 2018/Jun/10 14:15
 */
open class DefaultPreference(private val name: String) {

    private lateinit var preferences: SharedPreferences

    fun create(context: Context) {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun <T> getValue(keyName: String, defaultValue: T): T = with(preferences) {
        with(preferences) {
            if (defaultValue is String) {
                return getString(keyName, defaultValue) as T
            }
            if (defaultValue is Long) {
                return getLong(keyName, defaultValue) as T
            }
            if (defaultValue is Int) {
                return getInt(keyName, defaultValue) as T
            }
            if (defaultValue is Boolean) {
                return getBoolean(keyName, defaultValue) as T
            }
            if (defaultValue is Float) {
                return getFloat(keyName, defaultValue) as T
            }
        }
        throw IllegalArgumentException("Type not Allowed to save, Try primitive types.")
    }

    fun <T> putValue(keyName: String, value: T) = with(preferences.edit()) {
        when (value) {
            is Long -> putLong(keyName, value)
            is String -> putString(keyName, value)
            is Int -> putInt(keyName, value)
            is Boolean -> putBoolean(keyName, value)
            is Float -> putFloat(keyName, value)
            else -> throw IllegalArgumentException("Type not Allowed to save, Try primitive types.")
        }.apply()
    }

    fun clear() = preferences.edit().clear().apply()
}