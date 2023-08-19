package com.apex.codeassesment.data.repository

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.UserResponse
import com.apex.codeassesment.data.model.local.LocalDataSource
import com.apex.codeassesment.data.remote.RemoteDataSource
import com.apex.codeassesment.utils.NetworkResult
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : UserRepositoryInf {

    suspend fun getAllUser(format: String, range: Int): List<User>? {
        return remoteDataSource.getAllUser(format, range)
    }

    override suspend fun getUsers(): Flow<NetworkResult<UserResponse>> {
        return remoteDataSource.loadUsers()
    }

    override fun getSavedUser(): User? {
       return localDataSource.loadUser()
    }

    override suspend fun getUser(forceUpdate: Boolean): User? {
        return remoteDataSource.loadUser().results?.takeIf { it.isNotEmpty() }?.let {
            localDataSource.saveUser(it.get(0))
            it.get(0)
        }
    }


}