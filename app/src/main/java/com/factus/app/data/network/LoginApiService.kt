package com.factus.app.data.network

import retrofit2.http.POST

interface LoginApiService {

    @POST("")
    suspend fun loginFactus() {

    }
}