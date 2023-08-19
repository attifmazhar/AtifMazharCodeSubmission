package com.apex.codeassesment.data.remote

import com.apex.codeassesment.data.model.BaseApiResponse
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.UserResponse
import com.apex.codeassesment.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

// TODO (2 points): Add tests
class RemoteDataSource @Inject constructor(private val apiClient: APIClient): BaseApiResponse() {

  // TODO (5 points): Load data from endpoint https://randomuser.me/api
  suspend fun loadUser() = apiClient.getUser()

  // TODO (3 points): Load data from endpoint https://randomuser.me/api?results=10
  // TODO (Optional Bonus: 3 points): Handle succes and failure from endpoints

    suspend fun loadUsers(): Flow<NetworkResult<UserResponse>> {
        return flow {
            val fooList = safeApiCall { apiClient.getUserListWithFlow("", 10) }
            emit(fooList)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllUser(format: String, range: Int): List<User>? {
        val userResponse = apiClient.getUserList(format, range)
        return userResponse.results
    }

}
