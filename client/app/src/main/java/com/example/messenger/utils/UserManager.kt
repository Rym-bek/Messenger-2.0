package com.example.messenger.utils

import android.util.Log
import com.example.messenger.models.User

object UserManager {
    var user: User? = null
    private val listeners = mutableListOf<() -> Unit>()

    fun getUserModel(): User? {
        return user
    }

    fun setUserModel(newUser: User?) {
        user = newUser
        notifyListeners()
    }

    fun addListener(listener: () -> Unit) {
        listeners.add(listener)
    }

    fun removeListener(listener: () -> Unit) {
        listeners.remove(listener)
    }

    private fun notifyListeners() {
        listeners.forEach { it.invoke() }
    }
}