package com.factus.app.domain.repository

import com.factus.app.domain.models.Token
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveTokenData(token: Token)
    suspend fun tokenDataFlow(): Flow<Token?>
}