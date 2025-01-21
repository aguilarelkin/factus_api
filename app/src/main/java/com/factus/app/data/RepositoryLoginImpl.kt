package com.factus.app.data

import com.factus.app.data.network.LoginApiService
import com.factus.app.domain.models.Token
import com.factus.app.domain.repository.DataStoreRepository
import com.factus.app.domain.repository.LoginRepository
import com.factus.app.domain.state.LoginResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class RepositoryLoginImpl @Inject constructor(
    private val loginApiService: LoginApiService,
    private val dataStoreRepository: DataStoreRepository
) : LoginRepository {

    override suspend fun singInFactus(
        grantType: String,
        clientId: String,
        clientSecret: String,
        username: String,
        password: String
    ): LoginResult<Token> = runCatching {
        loginApiService.loginFactus(
            createRequestBody(grantType),
            createRequestBody(clientId),
            createRequestBody(clientSecret),
            createRequestBody(username),
            createRequestBody(password)
        ).toDomainModel()
    }.mapCatching {
        dataStoreRepository.saveTokenData(it)
        it
    }.fold(onSuccess = {
        return LoginResult.Success(it)
    }, onFailure = {
        return LoginResult.Error(it.message ?: "Unknown error")
    })

    override suspend fun refreshToken(
        grantType: String, clientId: String, clientSecret: String, refreshToken: String
    ): LoginResult<Token> = runCatching {
        loginApiService.refreshToken(
            createRequestBody(grantType),
            createRequestBody(clientId),
            createRequestBody(clientSecret),
            createRequestBody(refreshToken)
        ).toDomainModel()
    }.mapCatching {
        dataStoreRepository.saveTokenData(it)
        it
    }.fold(onSuccess = {
        return LoginResult.Success(it)
    }, onFailure = {
        return LoginResult.Error(it.message ?: "Unknown error")
    })

    private fun createRequestBody(value: String): RequestBody {
        return value.toRequestBody("multipart/form-data".toMediaType())
    }
}