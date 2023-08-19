package com.apex.codeassesment.data.repository

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.UserResponse
import com.apex.codeassesment.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface UserRepositoryInf {

    fun getSavedUser(): User?
    suspend fun getUser(forceUpdate: Boolean): User?
    suspend fun getUsers(): Flow<NetworkResult<UserResponse>>
}