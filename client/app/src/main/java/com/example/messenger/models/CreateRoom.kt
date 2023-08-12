package com.example.messenger.models

import com.google.gson.annotations.SerializedName

data class CreateRoom (
    @SerializedName("participants")
    val participants: List<String?>
        )