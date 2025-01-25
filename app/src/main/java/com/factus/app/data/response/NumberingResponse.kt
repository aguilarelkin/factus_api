package com.factus.app.data.response

import com.factus.app.domain.models.Numbering
import com.google.gson.annotations.SerializedName

data class NumberingResponse(
    @SerializedName("id") val id: Int, @SerializedName("document") val document: String
) {
    fun toDomainModel() = Numbering(id, document)
}
