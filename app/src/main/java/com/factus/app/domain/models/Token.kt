package com.factus.app.domain.models

data class Token(
    val token_type: String, val expires_in: String, val access_token: Int,
    val refresh_token: String
)
