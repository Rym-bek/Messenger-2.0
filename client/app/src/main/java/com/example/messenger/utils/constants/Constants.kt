package com.example.messenger.utils.constants

object Constants {
    const val WEB_SOCKET_URL= "ws://192.168.43.68:5000/"

    const val BASE_URL = "http://192.168.43.68:5000/"

    const val LOGIN_URL = "login"
    const val REGISTER_URL = "register"
    const val REFRESH_URL = "refresh"
    const val UPDATE_PROFILE_PHOTO_URL = "images/profileImage"
    const val GET_USER_URL = "user"
    const val GET_USERS_URL = "users"
    const val USER_UPDATE_URL = "user/update"

    const val DATA_STORE_NAME = "token_data"
    const val TOKEN = "token"

    const val TOKEN_HEADER = "x-access-token"

    const val JSON_URL= "url"

    const val IMAGE= "image"

    const val NICKNAME= "nickname"

    const val QUERY_ROOM_UID= "roomUid"

    const val QUERY_USER_UID= "userUid"

    const val MESSAGES= "messages"

    const val MESSAGES_PRIVATE= "/private"

    const val CREATE_ROOM= "/createRoom"

    const val LOCATIONS= "locations"

    const val UPDATE= "/update"

    const val ENABLE= "/enable"
    const val ENABLED= "enabled"
    const val UID= "uid"
    const val LOCATION_UPDATE_INTERVAL_MS:Long = 5000

    const val SHARED_PREFERENCES_STORAGE = "switch"
    const val SWITCH_STATE = "switchState"
}