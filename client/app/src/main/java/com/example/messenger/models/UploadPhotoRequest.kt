package com.example.messenger.models

import com.example.messenger.utils.constants.Constants
import com.google.gson.annotations.SerializedName
import java.io.File

data class UploadPhotoRequest(
    @SerializedName(Constants.IMAGE)
    val image: File
)
