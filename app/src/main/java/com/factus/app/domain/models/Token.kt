package com.factus.app.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val token_type: String, val expires_in: String, val access_token: String,
    val refresh_token: String
)
