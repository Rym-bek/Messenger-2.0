package com.example.messenger.repositories

import com.example.messenger.models.ProfileAuth
import com.example.messenger.retrofit.AuthService
import com.example.messenger.retrofit.MainService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val authService: AuthService){
    suspend fun createProfile(profileAuth: ProfileAuth) = authService.createProfile(profileAuth)
    suspend fun loginProfile(profileAuth: ProfileAuth) = authService.loginProfile(profileAuth)
}