package com.apex.codeassesment.data.local


interface PreferencesHelper {
    fun saveUser(user: String)
    fun loadUser(): String?
}