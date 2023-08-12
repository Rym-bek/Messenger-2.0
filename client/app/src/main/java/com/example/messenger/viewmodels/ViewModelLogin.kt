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
class ViewModelLogin @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    val loginResponse = MutableLiveData<BaseResponse<AuthResponse>>()

    fun loginAccount(profileAuth: ProfileAuth) {
        viewModelScope.launch {
            try {
                val response = repository.loginProfile(profileAuth)
                if (response.code() == 200) {
                    loginResponse.value = BaseResponse.Success(response.body())
                } else {
                    loginResponse.value = BaseResponse.Error(response.message())
                }
            } catch (ex: Exception) {
                loginResponse.value = BaseResponse.Error(ex.message)
            }
        }
    }
}



