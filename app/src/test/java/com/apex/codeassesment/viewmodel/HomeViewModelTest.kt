package com.apex.codeassesment.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.UserResponse
import com.apex.codeassesment.data.repository.UserRepositoryInf
import com.apex.codeassesment.ui.main.MainViewModel
import com.apex.codeassesment.utils.NetworkResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    // This rule ensures that LiveData updates are done on the same thread during tests
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepositoryInf

    @Mock
    private lateinit var userListObserver: Observer<NetworkResult<UserResponse>>

    @Mock
    private lateinit var userObserver: Observer<User>

    // The ViewModel being tested
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        mainViewModel = MainViewModel(userRepository)
    }

    @After
    fun tearDown() {
        // Do any necessary cleanup
    }

    @Test
    fun `test getUserList function`() = runTest {
        // Given
        val userResponse = UserResponse(/* Create a mock UserResponse here */)
        val networkResult = NetworkResult.Success(userResponse)

        // Configure userRepository's getUsers function to emit a flow with the defined networkResult
        `when`(userRepository.getUsers()).thenReturn(flow { emit(networkResult) })

        // Observe the userList LiveData
        mainViewModel.userList.observeForever(userListObserver)

        // When
        mainViewModel.getUserList()

        // Then
        // Verify that the userList LiveData is updated with the networkResult
        verify(userListObserver).onChanged(networkResult)

        // Clean up
        mainViewModel.userList.removeObserver(userListObserver)
    }

    @Test
    fun `test getUser function`() = runTest {
        // Given
        val user = User()

        `when`(userRepository.getUser(anyBoolean())).thenReturn(user)

        // Observe the user LiveData
        mainViewModel.user.observeForever(userObserver)

        // When
        mainViewModel.getUser(forceUpdate = false)

        // Then
        // Verify that the user LiveData is updated with the defined user
        verify(userObserver).onChanged(user)

        // Clean up
        mainViewModel.user.removeObserver(userObserver)
    }

}