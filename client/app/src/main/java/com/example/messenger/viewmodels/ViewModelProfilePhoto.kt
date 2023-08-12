package com.example.messenger.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.models.UploadPhotoResponse
import com.example.messenger.repositories.MainRepository
import com.example.messenger.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ViewModelProfilePhoto @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    val uploadPhotoResponse = MutableLiveData<BaseResponse<UploadPhotoResponse>>()


    fun uploadPhoto(file: File) {

        val body = MultipartBody.Part
            .createFormData(
                name = "image",
                filename = file.name,
                body = file.asRequestBody())

        viewModelScope.launch {
            try {
                val response = repository.updateProfilePhoto(body)
                if (response.code() == 200) {
                    uploadPhotoResponse.value = BaseResponse.Success(response.body())
                } else {
                    uploadPhotoResponse.value = BaseResponse.Error(response.message())
                }
            } catch (ex: Exception) {
                uploadPhotoResponse.value = BaseResponse.Error(ex.message)
            }
        }
    }
}