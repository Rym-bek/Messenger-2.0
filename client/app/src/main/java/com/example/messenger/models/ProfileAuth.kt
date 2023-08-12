package com.example.messenger.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class ProfileAuth(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)