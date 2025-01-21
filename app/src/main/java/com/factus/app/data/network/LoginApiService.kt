package com.factus.app.data.network

import com.factus.app.data.response.TokenResponse
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LoginApiService {

    @Multipart
    @POST("/oauth/token")
    suspend fun loginFactus(
        @Part("grant_type") grantType: RequestBody,
        @Part("client_id") clientId: RequestBody,
        @Part("client_secret") clientSecret: RequestBody,
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody
    ): TokenResponse

    @Multipart
    @POST("/oauth/token")
    suspend fun refreshToken(
        @Part("grant_type") grantType: RequestBody,
        @Part("client_id") clientId: RequestBody,
        @Part("client_secret") clientSecret: RequestBody,
        @Part("refresh_token") refreshToken: RequestBody
    ): TokenResponse
}