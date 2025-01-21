package com.factus.app.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.factus.app.domain.models.Token
import com.factus.app.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RepositoryDataStoreImpl @Inject constructor(private val context: Context) :
    DataStoreRepository {

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "app_preferences")
        private val TOKEN_DATA_KEY = stringPreferencesKey("token_data")
    }

    override suspend fun saveTokenData(token: Token) {
        val json = Json.encodeToString(token)
        context.dataStore.edit { preferences ->
            preferences[TOKEN_DATA_KEY] = json
        }
    }

    override suspend fun tokenDataFlow(): Flow<Token?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_DATA_KEY]?.let {
                Json.decodeFromString<Token>(it)
            }
        }
    }
}