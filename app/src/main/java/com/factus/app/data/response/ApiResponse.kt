package com.factus.app.data.response

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(@SerializedName("data") val data: List<T>)
data class ApiFactureResponse<T>(@SerializedName("data") val data: T)
data class InvoiceResponse<T>(@SerializedName("data") val data: ApiResponse<T>)

