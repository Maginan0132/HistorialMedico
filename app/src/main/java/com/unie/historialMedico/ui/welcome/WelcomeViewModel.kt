package com.unie.historialMedico.ui.welcome

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WelcomeViewModel(private val application: Application) : ViewModel() {

    private val sharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUser(name: String, pin: String) {
        with(sharedPreferences.edit()) {
            putString("USER_NAME", name)
            putString("USER_PIN", pin)
            apply()
        }
    }

    fun loadUser(): Pair<String?, String?> {
        val name = sharedPreferences.getString("USER_NAME", null)
        val pin = sharedPreferences.getString("USER_PIN", null)
        return Pair(name, pin)
    }
}

class WelcomeViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WelcomeViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
