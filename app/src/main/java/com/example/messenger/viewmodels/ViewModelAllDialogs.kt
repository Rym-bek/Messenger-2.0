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
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class ViewModelAllDialogs @Inject constructor(
    private val repository: MainRepository
): ViewModel() {
    //fun getLocationList(): MutableLiveData<ArrayList<Location>> = locationList

    val dialogs = MutableLiveData<BaseResponse<ArrayList<UserDialog>>>()

    fun loadDialogs(userUid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getAllDialogs(userUid)

                if (response.code() == 200) {
                    dialogs.postValue(BaseResponse.Success(response.body()))
                } else {
                    dialogs.postValue(BaseResponse.Error(response.message()))
                }
            } catch (ex: Exception) {
                BaseResponse.Error(ex.message).toString()
            }
        }
    }

    fun update(dialogs: ArrayList<UserDialog>)
    {
        this.dialogs.postValue(BaseResponse.Success(dialogs))
    }
}