package com.example.gramasuvidha.util

import android.content.Context

object PrefsManager {
    private const val PREFS_NAME = "grama_suvidha_prefs"
    private const val KEY_LANGUAGE = "is_kannada"

    fun isKannada(context: Context): Boolean =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_LANGUAGE, false)

    fun setKannada(context: Context, value: Boolean) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_LANGUAGE, value).apply()
}