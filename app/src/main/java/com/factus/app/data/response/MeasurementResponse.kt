package com.factus.app.data.response

import com.factus.app.domain.models.Measurement
import com.google.gson.annotations.SerializedName

data class MeasurementResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String
) {
    fun toDomainModel() = Measurement(id, code, name)
}
