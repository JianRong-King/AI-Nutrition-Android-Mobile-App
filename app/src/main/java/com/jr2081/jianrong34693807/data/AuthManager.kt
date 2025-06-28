package com.jr2081.jianrong34693807.data

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


object AuthManager {
    private const val PREF_NAME = "auth_prefs"
    private const val KEY_PATIENT_ID = "patient_id"

    private val patientIdState: MutableState<String?> = mutableStateOf(null)

    fun login(context: Context, userId: String) {
        patientIdState.value = userId

        // Save to SharedPreferences
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_PATIENT_ID, userId).apply()
    }

    fun logout(context: Context) {
        patientIdState.value = null

        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove(KEY_PATIENT_ID).apply()
    }

    fun getUserId(): String? {
        return patientIdState.value
    }

    fun isLoggedIn(): Boolean {
        return patientIdState.value != null
    }

    fun loadUserIdFromPrefs(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val savedId = prefs.getString(KEY_PATIENT_ID, null)
        patientIdState.value = savedId
    }
}