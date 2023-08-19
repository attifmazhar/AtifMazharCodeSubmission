package com.apex.codeassesment.data.remote

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIClient {
    @GET("api/")
    suspend fun getUser(
    ): UserResponse

    @GET("api/")
    suspend fun getUserList(
        @Query("format") format: String,
        @Query("results") results: Int
    ): UserResponse

    @GET("api/")
    suspend fun getUserListWithFlow(
        @Query("format") format: String,
        @Query("results") results: Int
    ): Response<UserResponse>
}