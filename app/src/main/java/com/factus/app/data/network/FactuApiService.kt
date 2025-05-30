package com.factus.app.data.network

import com.factus.app.data.response.ApiFactureResponse
import com.factus.app.data.response.ApiResponse
import com.factus.app.data.response.FactureResponse
import com.factus.app.data.response.InvoiceResponse
import com.factus.app.data.response.LocationResponse
import com.factus.app.data.response.MeasurementResponse
import com.factus.app.data.response.NumberingResponse
import com.factus.app.data.response.TributeResponse
import com.factus.app.data.response.invoiceitem.FactureItemResponse
import com.factus.app.data.response.pdf.PdfResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FactuApiService {

    @GET("/v1/numbering-ranges?filter[id]")
    suspend fun getNumberingRanges(): ApiResponse<NumberingResponse>

    @GET("/v1/measurement-units")
    suspend fun getUnitsMeasurement(): ApiResponse<MeasurementResponse>

    @GET("/v1/municipalities")
    suspend fun getLocations(): ApiResponse<LocationResponse>

    @GET("/v1/tributes/products")
    suspend fun getTributes(): ApiResponse<TributeResponse>

    @POST("/v1/bills/validate")
    suspend fun createdFacture(@Body facture: FactureResponse): ApiFactureResponse<FactureResponse>

    @GET("/v1/bills")
    suspend fun getInvoice(@Query("filter[identification]") identification: String): InvoiceResponse<FactureItemResponse>

    @GET("v1/bills/download-pdf/{number}")
    suspend fun downloadPdf(@Path("number") number: String): PdfResponse
}