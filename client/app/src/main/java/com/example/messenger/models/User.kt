package com.example.messenger.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date


data class User(
    @SerializedName("id")
    val id: Int?=null,
    @SerializedName("uid")
    val uid: String?=null,
    @SerializedName("email")
    val email: String?=null,
    @SerializedName("password")
    val password: String?=null,
    @SerializedName("phone")
    val phone: String?=null,
    @SerializedName("nickname")
    val nickname: String?=null,
    @SerializedName("photo_url")
    var photo_url: String?=null,
    @SerializedName("firstname")
    val firstname: String?=null,
    @SerializedName("lastname")
    val lastname: String?=null,
    @SerializedName("about")
    val about: String?=null,
    )

