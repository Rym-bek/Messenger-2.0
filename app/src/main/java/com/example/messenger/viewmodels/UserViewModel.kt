package com.example.messenger.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.models.User
import com.example.messenger.repositories.MainRepository
import com.example.messenger.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {
    var userData = MutableLiveData<User>()

    init{
        loadUser()
    }

    fun updateUserData(user: User) {
        userData.postValue(user)
    }

    private fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getUser()
                if (response.code() == 200) {
                    userData.postValue(response.body())
                }
            } catch (ex: Exception) {
                BaseResponse.Error(ex.message).toString()
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.updateUser(user)
                if (response.code() == 200) {
                    userData.postValue(response.body())
                }
            } catch (ex: Exception) {
                BaseResponse.Error(ex.message).toString()
            }
        }
    }
}