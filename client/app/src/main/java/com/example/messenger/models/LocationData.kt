package com.example.messenger.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.messenger.methods.CustomDateUtils
import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt


data class LocationData(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("latitude")
    val latitude: Double)



