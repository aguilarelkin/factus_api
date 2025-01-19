package com.factus.app.data

import com.factus.app.data.network.LoginApiService
import com.factus.app.domain.repository.LoginRepository
import javax.inject.Inject

class RepositoryLoginImpl @Inject constructor(private val loginApiService: LoginApiService) :
    LoginRepository {

    override suspend fun singInFactus() {
        loginApiService.loginFactus()
        TODO("Not yet implemented")
    }
}