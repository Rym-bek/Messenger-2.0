package com.example.messenger.models

import com.google.gson.annotations.SerializedName

data class Room (
    @SerializedName("room_uid")
    val roomUid: String
        )