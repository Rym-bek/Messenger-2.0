package com.example.messenger.retrofit

import com.example.messenger.models.*
import com.example.messenger.utils.constants.Constants
import com.example.messenger.utils.constants.RoutingConstants
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import java.util.ArrayList

interface MainService {

    @GET(Constants.GET_USER_URL)
    suspend fun getUser(): Response<User>

    @GET(Constants.GET_USERS_URL)
    suspend fun getUsers(
        @Query(Constants.NICKNAME) nickname: String?,
        @Query(Constants.QUERY_USER_UID) userUid:String): Response<ArrayList<UserDialog>>

    @GET(Constants.GET_USERS_URL+ RoutingConstants.UID)
    suspend fun getUserByUid(
        @Query(Constants.QUERY_USER_UID) userUid:String): Response<UserDialog>

    @POST(Constants.MESSAGES+ Constants.CREATE_ROOM)
    suspend fun createRoom(
        @Body createRoom: CreateRoom
    ): Response<Room>

    @GET(Constants.MESSAGES+ Constants.MESSAGES_PRIVATE)
    suspend fun getPrivateMessages(
        @Query(Constants.QUERY_ROOM_UID) roomUid:String
    ): Response<ArrayList<Message>>

    @GET(Constants.MESSAGES)
    suspend fun getAllDialogs(
        @Query(Constants.QUERY_USER_UID) userUid:String
    ): Response<ArrayList<UserDialog>>

    @Multipart
    @POST(Constants.UPDATE_PROFILE_PHOTO_URL)
    suspend fun updateProfilePhoto(
        @Part image: MultipartBody.Part
    ): Response<UploadPhotoResponse>

    @POST(Constants.USER_UPDATE_URL)
    suspend fun updateUser(
        @Body user: User
    ): Response<User>

    @POST(Constants.LOCATIONS+ Constants.UPDATE)
    suspend fun setLocation(
        @Body locationData: LocationData
    ):Response<Boolean>

    @POST(Constants.LOCATIONS)
    suspend fun getLocations(
        @Body locationData: LocationData
    ): Response<ArrayList<LocationUser>>

    @POST(Constants.LOCATIONS+ Constants.ENABLE)
    suspend fun enableLocation(
        @Query(Constants.UID) uid:String,
        @Query(Constants.ENABLED) enabled:Boolean):Response<Boolean>
}


