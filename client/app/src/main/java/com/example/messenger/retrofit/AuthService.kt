package com.example.messenger.retrofit

import com.example.messenger.models.AuthResponse
import com.example.messenger.models.ProfileAuth
import com.example.messenger.utils.constants.Constants
import retrofit2.Response
import retrofit2.http.*

interface AuthService {
    @POST(Constants.REGISTER_URL)
    suspend fun createProfile(
        @Body profileAuth: ProfileAuth
    ): Response<AuthResponse>

    @POST(Constants.LOGIN_URL)
    suspend fun loginProfile(
        @Body profileAuth: ProfileAuth
    ): Response<AuthResponse>

    @GET(Constants.REFRESH_URL)
    suspend fun refreshToken(
        @Header(Constants.TOKEN_HEADER) token: String,
    ): Response<AuthResponse>
}