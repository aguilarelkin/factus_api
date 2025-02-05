package com.factus.app.data

import com.factus.app.data.network.FactuApiService
import com.factus.app.data.response.FactureResponse
import com.factus.app.domain.models.Facture
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
            val cachedData = dataStoreRepository.getDocuments()
            if (cachedData.isNotEmpty()) {
                return LoginResult.Success(cachedData)
            }
            factuApiService.getNumberingRanges().data.map {
                it.toDomainModel()
            }
        }.mapCatching {
            dataStoreRepository.saveDocuments(it)
            it
        }.fold(onSuccess = {
            return LoginResult.Success(it)
        }, onFailure = {
            return LoginResult.Error(it.message ?: "Unknown error")
        })
    }

    override suspend fun getUnitsMeasurement(): LoginResult<List<Measurement>> {
        runCatching {
            val cachedData = dataStoreRepository.getUnits()
            if (cachedData.isNotEmpty()) {
                return LoginResult.Success(cachedData)
            }
            factuApiService.getUnitsMeasurement().data.map { it.toDomainModel() }
        }.mapCatching {
            dataStoreRepository.saveUnits(it)
            it
        }.fold(onSuccess = {
            return LoginResult.Success(it)
        }, onFailure = {
            return LoginResult.Error(it.message ?: "Unknown error")
        })
    }

    override suspend fun getLocations(): LoginResult<List<Location>> {
        runCatching {
            val cachedData = dataStoreRepository.getLocations()
            if (cachedData.isNotEmpty()) {
                return LoginResult.Success(cachedData)
            }
            factuApiService.getLocations().data.map { it.toDomainModel() }
        }.mapCatching {
            dataStoreRepository.saveLocations(it)
            it
        }.fold(onSuccess = {
            return LoginResult.Success(it)
        }, onFailure = {
            return LoginResult.Error(it.message ?: "Unknown error")
        })
    }

    override suspend fun getTributes(): LoginResult<List<Tribute>> {
        runCatching {
            val cachedData = dataStoreRepository.getTaxes()
            if (cachedData.isNotEmpty()) {
                return LoginResult.Success(cachedData)
            }
            factuApiService.getTributes().data.map { it.toDomainModel() }
        }.mapCatching {
            dataStoreRepository.saveTaxes(it)
            it
        }.fold(onSuccess = {
            return LoginResult.Success(it)
        }, onFailure = {
            return LoginResult.Error(it.message ?: "Unknown error")
        })
    }

    override suspend fun createFacture(facture: FactureResponse): LoginResult<Facture> {
        runCatching {
            factuApiService.createdFacture(facture).data.toDomain()
        }.fold(onSuccess = {
            return LoginResult.Success(it)
        }, onFailure = {
            return LoginResult.Error(it.message ?: "Unknown error")
        })
    }

    override suspend fun getInvoice(identification: String): LoginResult<List<Facture>> {
        runCatching {
            factuApiService.getInvoice(identification).data.map { it.toDomain() }
        }.fold(onSuccess = {
            return LoginResult.Success(it)
        }, onFailure = {
            return LoginResult.Error(it.message ?: "Unknown error")
        })
    }
}