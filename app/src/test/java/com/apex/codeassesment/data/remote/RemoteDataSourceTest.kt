package com.apex.codeassesment.data.remote

import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.UserResponse
import com.apex.codeassesment.utils.NetworkResult
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class RemoteDataSourceTest {

    @Mock
    private lateinit var mockApiClient: APIClient

    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        remoteDataSource = RemoteDataSource(mockApiClient)
    }

    @Test
    fun `loadUser should call apiClient getUser`() = runTest {
        // Given
        val expectedUser = User()
        `when`(mockApiClient.getUser()).thenReturn(expectedUser)

        // When
        val result = remoteDataSource.loadUser()

        // Then
        assert(result == expectedUser)
    }

    @Test
    fun `loadUsers should emit a list of users`() = runTest {
        // Given
        val expectedUserList = listOf(User(), User()) // Create expected User objects
        val expectedNetworkResult = NetworkResult.Success(expectedUserList)
        `when`(mockApiClient.getUserListWithFlow("", 10)).thenReturn(expectedNetworkResult)

        // When
        val flow = remoteDataSource.loadUsers()
        val resultList = mutableListOf<NetworkResult<UserResponse>>()
        flow.collect { resultList.add(it) }

        // Then
        assert(resultList.size == 1)
        assert(resultList[0] == expectedNetworkResult)
    }

    @Test
    fun `getAllUser should return a list of users`() = runTest {
        // Given
        val expectedUserList = listOf(User(), User()) // Create expected User objects
        val expectedUserResponse = UserResponse(expectedUserList)
        `when`(mockApiClient.getUserList("", 10)).thenReturn(expectedUserResponse)

        // When
        val result = remoteDataSource.getAllUser("", 10)

        // Then
        assert(result == expectedUserList)
    }
}