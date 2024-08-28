package com.paltech.ontheshelf.di
// UserRepository.kt


import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class UserRepository @Inject constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    fun setUserLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun logout(){
        sharedPreferences.edit().clear()
    }
}
