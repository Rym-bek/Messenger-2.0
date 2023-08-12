package com.example.messenger.models

import com.example.messenger.utils.constants.Constants
import com.google.gson.annotations.SerializedName

data class UploadPhotoResponse(
    @SerializedName(Constants.JSON_URL)
    val url: String
)
