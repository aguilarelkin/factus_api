package com.factus.app.data.response.pdf

import com.factus.app.domain.models.pdf.PdfData
import com.google.gson.annotations.SerializedName

data class PdfDataResponse(
    @SerializedName("file_name") val fileName: String,
    @SerializedName("pdf_base_64_encoded") val pdfBase64: String?
) {
    fun toDomainModel() = PdfData(fileName, pdfBase64)
}
