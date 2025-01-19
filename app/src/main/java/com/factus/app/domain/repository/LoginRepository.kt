package com.factus.app.domain.repository

import com.factus.app.domain.models.Token
import com.factus.app.domain.state.LoginResult

interface LoginRepository {
    suspend fun singInFactus(
        grantType: String,
        clientId: String,
        clientSecret: String,
        username: String,
        password: String
    ): LoginResult<Token>
}