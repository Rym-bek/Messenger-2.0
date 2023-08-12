package com.example.messenger.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.messenger.methods.CustomDateUtils
import com.google.gson.annotations.SerializedName

data class UserDialog (
    @SerializedName("room_uid")
    var roomUid: String?=null,
    @SerializedName("another_uid")
    val anotherUid: String?=null,
    @SerializedName("my_uid")
    val myUid: String?=null,
    @SerializedName("firstname")
    val firstname: String?=null,
    @SerializedName("lastname")
    val lastname: String?=null,
    @SerializedName("nickname")
    val nickname: String?=null,
    @SerializedName("photo_url")
    var photo_url: String?=null,
    @SerializedName("message")
    var message: String?=null,
    @SerializedName("time")
    var time: String?=null,
    @SerializedName("online")
    var online: Boolean?=null,
        ){
    val formattedTime: String?
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            if (time != null)
            {
                return CustomDateUtils.formatTimestamp(time!!)
            }
            else
            {
                return null
            }
        }
}
