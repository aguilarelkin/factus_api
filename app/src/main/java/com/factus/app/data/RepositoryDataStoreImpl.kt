package com.factus.app.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.factus.app.domain.models.Location
import com.factus.app.domain.models.Measurement
import com.factus.app.domain.models.Numbering
import com.factus.app.domain.models.Token
import com.factus.app.domain.models.Tribute
import com.factus.app.domain.repository.DataStoreRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RepositoryDataStoreImpl @Inject constructor(private val context: Context) :
    DataStoreRepository {

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "app_preferences")
        private val TOKEN_DATA_KEY = stringPreferencesKey("token_data")
        private val NUMBERING_DATA_KEY = stringPreferencesKey("numbering_data")
        private val LOCATIONS_KEY = stringPreferencesKey("locations_key")
        private val UNITS_KEY = stringPreferencesKey("units_key")
        private val TAXES_KEY = stringPreferencesKey("taxes_key")
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

    override suspend fun saveDocuments(documents: List<Numbering>) {
        val json = documents.joinToString(separator = ",") {
            """{"id": ${it.id}, "document": "${it.document}"}"""
        }
        context.dataStore.edit { preferences ->
            preferences[NUMBERING_DATA_KEY] = "[$json]"
        }
    }

    override suspend fun getDocuments(): List<Numbering> {
        val json = context.dataStore.data.first()[NUMBERING_DATA_KEY] ?: "[]"
        return parseJsonToDocuments(json)
    }

    override suspend fun saveLocations(locations: List<Location>) {
        val json = Gson().toJson(locations)
        context.dataStore.edit { preferences ->
            preferences[LOCATIONS_KEY] = json
        }
    }

    override suspend fun getLocations(): List<Location> {
        val json = context.dataStore.data.first()[LOCATIONS_KEY] ?: "[]"
        return Gson().fromJson(json, object : TypeToken<List<Location>>() {}.type)
    }

    override suspend fun saveUnits(units: List<Measurement>) {
        val json = Gson().toJson(units)
        context.dataStore.edit { preferences ->
            preferences[UNITS_KEY] = json
        }
    }

    override suspend fun getUnits(): List<Measurement> {
        val json = context.dataStore.data.first()[UNITS_KEY] ?: "[]"
        return Gson().fromJson(json, object : TypeToken<List<Measurement>>() {}.type)
    }

    override suspend fun saveTaxes(taxes: List<Tribute>) {
        val json = Gson().toJson(taxes)
        context.dataStore.edit { preferences ->
            preferences[TAXES_KEY] = json
        }
    }

    override suspend fun getTaxes(): List<Tribute> {
        val json = context.dataStore.data.first()[TAXES_KEY] ?: "[]"
        return Gson().fromJson(json, object : TypeToken<List<Tribute>>() {}.type)
    }

    private fun parseJsonToDocuments(json: String): List<Numbering> {
        val documentList = mutableListOf<Numbering>()
        val regex = """\{"id": (\d+), "document": "(.*?)"\}""".toRegex()
        val matches = regex.findAll(json)
        for (match in matches) {
            val id = match.groupValues[1].toInt()
            val document = match.groupValues[2]
            documentList.add(Numbering(id, document))
        }
        return documentList
    }
}