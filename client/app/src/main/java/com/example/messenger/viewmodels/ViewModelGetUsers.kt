package com.example.messenger.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.models.UserDialog
import com.example.messenger.repositories.MainRepository
import com.example.messenger.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class ViewModelGetUsers @Inject constructor(
    private val repository: MainRepository
): ViewModel() {
    val users = MutableLiveData<BaseResponse<ArrayList<UserDialog>>>()

    fun loadUsers(nickname: String?, userUid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getUsers(nickname,userUid)
                if (response.code() == 200) {
                    users.postValue(BaseResponse.Success(response.body()))
                } else {
                    users.postValue(BaseResponse.Error(response.message()))
                }
            } catch (ex: Exception) {
                BaseResponse.Error(ex.message).toString()
            }
        }
    }
}