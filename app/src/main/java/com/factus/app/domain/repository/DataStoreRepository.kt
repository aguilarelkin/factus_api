package com.factus.app.domain.repository

import com.factus.app.domain.models.Location
import com.factus.app.domain.models.Measurement
import com.factus.app.domain.models.Numbering
import com.factus.app.domain.models.Token
import com.factus.app.domain.models.Tribute
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveTokenData(token: Token)
    suspend fun tokenDataFlow(): Flow<Token?>

    suspend fun saveDocuments(documents: List<Numbering>)
    suspend fun getDocuments(): List<Numbering>

    suspend fun saveLocations(locations: List<Location>)
    suspend fun getLocations(): List<Location>

    suspend fun saveUnits(units: List<Measurement>)
    suspend fun getUnits(): List<Measurement>

    suspend fun saveTaxes(taxes: List<Tribute>)
    suspend fun getTaxes(): List<Tribute>

}