package com.apex.codeassesment.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.apex.codeassesment.data.model.UserResponse
import com.apex.codeassesment.utils.NetworkResult
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    // This rule ensures that LiveData updates are done on the same thread during tests
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

     private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        // Create a fake ViewModel with mocked dependencies
        viewModel = mock(MainViewModel::class.java)
        `when`(viewModel.userList).thenReturn(MutableLiveData())
        `when`(viewModel.user).thenReturn(MutableLiveData())

        // Set up ViewModelProvider to return the fake ViewModel
        val viewModelProviderFactory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModel as T
            }
        }

        // Set up ViewModelStoreOwner to return the fake ViewModel
        val viewModelStoreOwner = object : ViewModelStoreOwner {
            override fun getViewModelStore(): ViewModelStore {
                return ViewModelStore()
            }
        }

        // Initialize the ViewModelProvider with the fake ViewModel
        val viewModelProvider = ViewModelProvider(viewModelStoreOwner, viewModelProviderFactory)

        // Create an instance of MainActivity
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity { activity ->
            // Set the ViewModelProvider to use the fake ViewModel
//            activity.viewModelFactory = viewModelProviderFactory
//            activity.viewModelStore = viewModelStoreOwner.getViewModelStore()
//            activity.viewModel = viewModel
        }
    }

    @Test
    fun testRefreshButtonClicked() {
        val userResponse = UserResponse(/* Create a mock UserResponse here */)
        val networkResult = NetworkResult.Success(userResponse)
        `when`(viewModel.getUser(anyBoolean())).thenAnswer { networkResult }
    }

}
