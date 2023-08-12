package com.example.messenger.models

import com.google.gson.annotations.SerializedName

data class OnlineStatus(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("online")
    val online: Boolean,
)