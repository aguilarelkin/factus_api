package com.factus.app.data.response

import com.factus.app.domain.models.Token
import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("token_type") val token_type: String,
    @SerializedName("expires_in") val expires_in: String,
    @SerializedName("access_token") val access_token: String,
    @SerializedName("refresh_token") val refresh_token: String
) {
    fun toDomainModel() = Token(token_type, expires_in, access_token, refresh_token)
}
