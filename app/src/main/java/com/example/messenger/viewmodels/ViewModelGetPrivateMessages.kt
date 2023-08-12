package com.example.messenger.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.models.Message
import com.example.messenger.models.User
import com.example.messenger.repositories.MainRepository
import com.example.messenger.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class ViewModelGetPrivateMessages @Inject constructor(
    private val repository: MainRepository
): ViewModel() {
    val messages = MutableLiveData<BaseResponse<ArrayList<Message>>?>()

    fun loadMessages(roomUid:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getPrivateMessages(roomUid)

                if (response.code() == 200) {
                    messages.postValue(BaseResponse.Success(response.body()))
                } else {
                    messages.postValue(BaseResponse.Error(response.message()))
                }
            } catch (ex: Exception) {
                BaseResponse.Error(ex.message).toString()
            }
        }
    }

    fun clear() {
        messages.value=null
    }

}