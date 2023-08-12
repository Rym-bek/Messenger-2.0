package com.example.messenger.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.models.CreateRoom
import com.example.messenger.models.Room
import com.example.messenger.models.UploadPhotoResponse
import com.example.messenger.repositories.MainRepository
import com.example.messenger.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ViewModelCreateRoom @Inject constructor(
    private val repository: MainRepository
): ViewModel() {
    val roomUid = MutableLiveData<BaseResponse<Room>>()

     fun createRoom(createRoom: CreateRoom) {
        viewModelScope.launch {
            try {
                val response = repository.createRoom(createRoom)
                if (response.code() == 200) {
                    roomUid.value = BaseResponse.Success(response.body())
                } else {
                    roomUid.value = BaseResponse.Error(response.message())
                }
            } catch (ex: Exception) {
                roomUid.value = BaseResponse.Error(ex.message)
            }
        }
    }
}