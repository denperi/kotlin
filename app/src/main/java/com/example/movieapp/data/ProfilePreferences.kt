package com.example.movieapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.profileDataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore("user_profile")

class ProfilePreferences(private val context: Context) {

    companion object {
        private val FULL_NAME_KEY = stringPreferencesKey("full_name")
        private val AVATAR_URI_KEY = stringPreferencesKey("avatar_uri")
        private val RESUME_URL_KEY = stringPreferencesKey("resume_url")
        private val POSITION_KEY = stringPreferencesKey("position")
    }

    suspend fun saveProfile(profile: Profile) {
        context.profileDataStore.edit { prefs ->
            prefs[FULL_NAME_KEY] = profile.fullName
            prefs[AVATAR_URI_KEY] = profile.avatarUri
            prefs[RESUME_URL_KEY] = profile.resumeUrl
            prefs[POSITION_KEY] = profile.position
        }
    }

    suspend fun getProfile(): Profile {
        val prefs = context.profileDataStore.data.first()
        return Profile(
            fullName = prefs[FULL_NAME_KEY] ?: "",
            avatarUri = prefs[AVATAR_URI_KEY] ?: "",
            resumeUrl = prefs[RESUME_URL_KEY] ?: "",
            position = prefs[POSITION_KEY] ?: ""
        )
    }
}