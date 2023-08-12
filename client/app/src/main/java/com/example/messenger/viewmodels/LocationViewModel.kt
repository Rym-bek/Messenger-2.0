package com.example.messenger.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.models.LocationData
import com.example.messenger.models.LocationUser
import com.example.messenger.repositories.MainRepository
import com.example.messenger.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class LocationViewModel@Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    val locations = MutableLiveData<BaseResponse<ArrayList<LocationUser>>?>()
    var setStatus = MutableLiveData<Boolean?>()
    var enableStatus = MutableLiveData<Boolean?>()

    fun setLocation(locationData: LocationData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.setLocation(locationData)
                if (response.code() == 200) {
                    setStatus.postValue(true)
                }
                else
                {
                    setStatus.postValue(false)
                }
            } catch (ex: Exception) {
                BaseResponse.Error(ex.message).toString()
            }
        }
    }

    fun getLocations(locationData: LocationData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getLocations(locationData)
                if (response.code() == 200) {
                    locations.postValue(BaseResponse.Success(response.body()))
                } else {
                    locations.postValue(BaseResponse.Error(response.message()))
                }
            } catch (ex: Exception) {
                BaseResponse.Error(ex.message).toString()
            }
        }
    }

    fun enableLocation(uid: String, enabled:Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.enableLocation(uid,enabled)
                if (response.code() == 200) {
                    enableStatus.postValue(true)
                }
                else
                {
                    enableStatus.postValue(false)
                }
            } catch (ex: Exception) {
                BaseResponse.Error(ex.message).toString()
            }
        }
    }
}