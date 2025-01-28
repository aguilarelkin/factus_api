package com.factus.app.data.network

import com.factus.app.data.response.LocationResponse
import com.factus.app.data.response.MeasurementResponse
import com.factus.app.data.response.NumberingResponse
import com.factus.app.data.response.TributeResponse
import retrofit2.http.GET

interface FactuApiService {

    @GET("/v1/numbering-ranges?filter[id]")
    suspend fun getNumberingRanges(): List<NumberingResponse>

    @GET("/v1/measurement-units")
    suspend fun getUnitsMeasurement(): List<MeasurementResponse>

    @GET("/v1/municipalities")
    suspend fun getLocations(): List<LocationResponse>

    @GET("/v1/tributes/products")
    suspend fun getTributes(): List<TributeResponse>

}