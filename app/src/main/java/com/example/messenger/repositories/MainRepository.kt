package com.example.messenger.repositories


import com.example.messenger.models.CreateRoom
import com.example.messenger.models.LocationData
import com.example.messenger.models.User
import com.example.messenger.retrofit.MainService
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val mainService : MainService){
    suspend fun updateProfilePhoto(file: MultipartBody.Part)=mainService.updateProfilePhoto(file)

    suspend fun getUser()=mainService.getUser()

    suspend fun updateUser(user: User)=mainService.updateUser(user)

    suspend fun getUsers(nickname: String?, userUid:String)=mainService.getUsers(nickname,userUid)

    suspend fun getPrivateMessages(roomUid: String)=mainService.getPrivateMessages(roomUid)

    suspend fun getAllDialogs(userUid: String)=mainService.getAllDialogs(userUid)

    suspend fun createRoom(createRoom: CreateRoom)=mainService.createRoom(createRoom)

    suspend fun setLocation(locationData: LocationData)=mainService.setLocation(locationData)

    suspend fun getLocations(locationData: LocationData)=mainService.getLocations(locationData)

    suspend fun enableLocation(uid: String, enabled:Boolean)=mainService.enableLocation(uid,enabled)

    suspend fun getUserByUid(userUid:String)=mainService.getUserByUid(userUid)
}

