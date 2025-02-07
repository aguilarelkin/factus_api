package com.factus.app.data.response.pdf

import com.factus.app.domain.models.pdf.Pdf
import com.google.gson.annotations.SerializedName

data class PdfResponse(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: PdfDataResponse?
) {
    fun toDomainModel() = Pdf(status, message, data?.toDomainModel())
}
