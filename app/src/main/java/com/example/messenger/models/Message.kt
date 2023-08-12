package com.example.messenger.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.messenger.methods.CustomDateUtils
import com.google.gson.annotations.SerializedName
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class Message(
    @SerializedName("sender_uid")
    val senderUid: String,
    @SerializedName("room_uid")
    val roomUid: String?=null,
    @SerializedName("message")
    val message: String,
    @SerializedName("time")
    val time: String?=null) {
    val formattedTime: String?
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            return CustomDateUtils.formatTimestamp(time!!)
        }
}



