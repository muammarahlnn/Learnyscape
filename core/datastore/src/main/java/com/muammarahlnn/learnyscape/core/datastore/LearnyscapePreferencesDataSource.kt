package com.muammarahlnn.learnyscape.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LearnyscapePreferencesDataStore, 06/10/2023 21.06 by Muammar Ahlan Abimanyu
 */

val Context.learnyscapePreferences: DataStore<Preferences> by preferencesDataStore("learnyscape")

class LearnyscapePreferencesDataSource @Inject constructor(
    private val preferences: DataStore<Preferences>
) {

    private object PreferencesKeys {

        val ACCESS_TOKEN = stringPreferencesKey("access_token")
    }

    suspend fun saveAccessToken(accessToken: String) {
        preferences.edit { pref ->
            pref[PreferencesKeys.ACCESS_TOKEN] = accessToken
        }
    }
}