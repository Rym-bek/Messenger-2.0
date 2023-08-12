package com.example.messenger.models

import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.messenger.R
import com.example.messenger.methods.CustomDateUtils
import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

data class LocationUser(
    @SerializedName("uid")
    val uid: String?=null,
    @SerializedName("photo_url")
    var photo_url: String?=null,
    @SerializedName("firstname")
    val firstname: String?=null,
    @SerializedName("lastname")
    val lastname: String?=null,
    @SerializedName("nickname")
    val nickname: String?=null,
    @SerializedName("distance")
    val distance: Double?=null,
){
    val formattedDistance: String
        get() {
            return distance?.roundToInt().toString()
        }
}
