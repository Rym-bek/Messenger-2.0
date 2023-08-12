package com.example.messenger.hilt

import android.content.Context
import com.example.messenger.retrofit.AuthService
import com.example.messenger.retrofit.MainService
import com.example.messenger.utils.AuthInterceptor
import com.example.messenger.utils.constants.Constants
import com.example.messenger.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun providesBaseUrl() : String = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager = TokenManager(context)

    @Singleton
    @Provides
    fun provideRetrofitBuilder(BASE_URL : String): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @Singleton
    fun provideMainService(okHttpClient: OkHttpClient, retrofit : Retrofit.Builder) : MainService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(MainService::class.java)

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit.Builder): AuthService =
        retrofit
            .build()
            .create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)
}