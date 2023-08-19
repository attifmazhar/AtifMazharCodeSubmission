package com.apex.codeassesment.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.data.model.UserResponse
import com.apex.codeassesment.data.repository.UserRepositoryInf
import com.apex.codeassesment.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepositoryInf) : ViewModel() {

    private val _userList : MutableLiveData<NetworkResult<UserResponse>> = MutableLiveData()
    val userList: LiveData<NetworkResult<UserResponse>> = _userList

    private val _user : MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user

    fun getUserList() {
        viewModelScope.launch {
            repository.getUsers().collect { values ->
                _userList.value = values
            }
        }
    }

    fun getUser(forceUpdate: Boolean) {
        viewModelScope.launch {
            _user.value = repository.getUser(forceUpdate)
        }
    }
}