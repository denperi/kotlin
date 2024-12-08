package com.example.movieapp.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "filter_preferences")

class FilterPreferences(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val GENRE_KEY = stringPreferencesKey("genre")
        val YEAR_KEY = stringPreferencesKey("year")
        val RUSSIAN_ONLY_KEY = booleanPreferencesKey("russian_only")
    }

    val filterSettings: Flow<FilterSettings> = dataStore.data.map { preferences ->
        FilterSettings(
            genre = preferences[GENRE_KEY] ?: "",
            year = preferences[YEAR_KEY] ?: "",
            russianOnly = preferences[RUSSIAN_ONLY_KEY] ?: false
        )
    }

    suspend fun saveFilterSettings(settings: FilterSettings) {
        dataStore.edit { preferences ->
            preferences[GENRE_KEY] = settings.genre
            preferences[YEAR_KEY] = settings.year
            preferences[RUSSIAN_ONLY_KEY] = settings.russianOnly
        }
    }
}

data class FilterSettings(
    val genre: String,
    val year: String,
    val russianOnly: Boolean
)