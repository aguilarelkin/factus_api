package com.factus.app.data.response

import com.factus.app.domain.models.Tribute
import com.google.gson.annotations.SerializedName

data class TributeResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String
) {
    fun toDomainModel() = Tribute(id, code, name, description)
}
