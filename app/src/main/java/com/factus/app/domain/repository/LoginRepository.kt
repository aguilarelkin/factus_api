package com.factus.app.domain.repository

interface LoginRepository {
    suspend fun singInFactus()
}