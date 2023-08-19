package com.apex.codeassesment.data.model.local

import com.apex.codeassesment.data.local.PreferencesHelper
import com.apex.codeassesment.data.local.PreferencesManager
import com.apex.codeassesment.data.model.User
import com.google.gson.Gson
import javax.inject.Inject

// TODO (3 points): Convert to Kotlin
// TODO (2 point): Add tests
// TODO (1 point): Use the correct naming conventions.
// TODO (3 points): Inject all dependencies instead of instantiating them.
class LocalDataSource @Inject constructor(
    val prefs: PreferencesHelper,
    val gson: Gson
) {
    fun loadUser(): User? {
        return prefs.loadUser()?.let {
            gson.fromJson(it, User::class.java)
        }

    }

    fun saveUser(user: User) {
        prefs.saveUser(gson.toJson(user))
    }
}