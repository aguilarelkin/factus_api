package com.factus.app.data.response.invoiceitem

import com.factus.app.domain.models.invoice.Document
import com.google.gson.annotations.SerializedName

data class DocumentResponse(
    @SerializedName("code") val code: String, @SerializedName("name") val name: String
) {
    fun toDomainModel() = Document(
        code, name
    )
}
