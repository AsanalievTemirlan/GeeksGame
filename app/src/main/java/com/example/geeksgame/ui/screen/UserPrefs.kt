package com.example.geeksgame.ui.screen

import android.content.Context

class UserPrefs(context: Context) {

    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_IS_REGISTERED = "is_registered"
    }

    fun saveUserId(id: String) {
        prefs.edit().putString(KEY_USER_ID, id).apply()
    }

    fun getUserId(): String? {
        return prefs.getString(KEY_USER_ID, null)
    }

    fun setRegistered(registered: Boolean) {
        prefs.edit().putBoolean(KEY_IS_REGISTERED, registered).apply()
    }

    fun isRegistered(): Boolean {
        return prefs.getBoolean(KEY_IS_REGISTERED, false)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
