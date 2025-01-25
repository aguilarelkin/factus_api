package com.factus.app.data.response

import com.factus.app.domain.models.Location
import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("department") val department: String
) {
    fun toDomainModel() = Location(id, code, name, department)
}
