package com.factus.app.data.network

import com.factus.app.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val tokenData = runBlocking {
            dataStoreRepository.tokenDataFlow().first()
        }

        val originalRequest = chain.request()

        return if (!tokenData?.access_token.isNullOrBlank()) {
            val modifiedRequest = originalRequest.newBuilder()
                .header("Authorization", "${tokenData?.token_type} ${tokenData?.access_token}")
                .build()
            chain.proceed(modifiedRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }
}