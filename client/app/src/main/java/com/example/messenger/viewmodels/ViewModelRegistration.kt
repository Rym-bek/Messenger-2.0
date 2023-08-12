package com.example.messenger.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.models.AuthResponse
import com.example.messenger.models.ProfileAuth
import com.example.messenger.repositories.AuthRepository
import com.example.messenger.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelRegistration @Inject constructor(
    private val repository: AuthRepository
    ): ViewModel() {

    val createResponse = MutableLiveData<BaseResponse<AuthResponse>>()

    fun createAccount(profileAuth: ProfileAuth) {
        viewModelScope.launch {
            try {
                val response = repository.createProfile(profileAuth)
                if (response.code() == 200) {
                    createResponse.value = BaseResponse.Success(response.body())
                } else {
                    createResponse.value = BaseResponse.Error(response.message())
                }
            } catch (ex: Exception) {
                createResponse.value = BaseResponse.Error(ex.message)
            }
        }
    }
}

