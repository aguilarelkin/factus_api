package com.factus.app.data

import android.util.Log
import com.factus.app.data.network.FactuApiService
import com.factus.app.domain.models.Location
import com.factus.app.domain.models.Measurement
import com.factus.app.domain.models.Numbering
import com.factus.app.domain.models.Tribute
import com.factus.app.domain.repository.DataStoreRepository
import com.factus.app.domain.repository.FactureRepository
import com.factus.app.domain.state.LoginResult
import javax.inject.Inject

class RepositoryFactureImpl @Inject constructor(
    private val factuApiService: FactuApiService,
    private val dataStoreRepository: DataStoreRepository
) : FactureRepository {
    override suspend fun getNumberingRanges(): LoginResult<List<Numbering>> {
        runCatching {
            factuApiService.getNumberingRanges().map { it.toDomainModel() }
        }.mapCatching {
            dataStoreRepository.saveDocuments(it)
            Log.e("getDocuments", dataStoreRepository.getDocuments().toString())
            it
        }.fold(onSuccess = {
            return LoginResult.Success(it)
        }, onFailure = {
            return LoginResult.Error(it.message ?: "Unknown error")
        })
    }

    override suspend fun getUnitsMeasurement(): LoginResult<List<Measurement>> {
        runCatching {
            factuApiService.getUnitsMeasurement().map { it.toDomainModel() }
        }.mapCatching {
            dataStoreRepository.saveUnits(it)
            Log.e("getUnits", dataStoreRepository.getUnits().toString())
            it
        }.fold(onSuccess = {
            return LoginResult.Success(it)
        }, onFailure = {
            return LoginResult.Error(it.message ?: "Unknown error")
        })
    }

    override suspend fun getLocations(): LoginResult<List<Location>> {
        runCatching {
            factuApiService.getLocations().map { it.toDomainModel() }
        }.mapCatching {
            dataStoreRepository.saveLocations(it)
            Log.e("getLocations", dataStoreRepository.getLocations().toString())
            it
        }.fold(onSuccess = {
            return LoginResult.Success(it)
        }, onFailure = {
            return LoginResult.Error(it.message ?: "Unknown error")
        })
    }

    override suspend fun getTributes(): LoginResult<List<Tribute>> {
        runCatching {
            factuApiService.getTributes().map { it.toDomainModel() }
        }.mapCatching {
            dataStoreRepository.saveTaxes(it)
            Log.e("getTributes", dataStoreRepository.getTaxes().toString())
            it
        }.fold(onSuccess = {
            return LoginResult.Success(it)
        }, onFailure = {
            return LoginResult.Error(it.message ?: "Unknown error")
        })
    }

}