package com.example.messenger.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.models.User
import com.example.messenger.models.UserDialog
import com.example.messenger.repositories.MainRepository
import com.example.messenger.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class GetUserByUidViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {
    var userData = MutableLiveData<BaseResponse<UserDialog>>()

    fun getUserByUid(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getUserByUid(uid)
                if (response.code() == 200) {
                    userData.postValue(BaseResponse.Success(response.body()))
                } else {
                    userData.postValue(BaseResponse.Error(response.message()))
                }
            } catch (ex: Exception) {
                userData.postValue(BaseResponse.Error(ex.message))
            }
        }
    }
}

