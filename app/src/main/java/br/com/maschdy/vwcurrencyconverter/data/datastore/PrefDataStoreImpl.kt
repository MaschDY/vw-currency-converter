package br.com.maschdy.vwcurrencyconverter.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import br.com.maschdy.vwcurrencyconverter.domain.datastore.PrefDataStore
import br.com.maschdy.vwcurrencyconverter.domain.model.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrefDataStoreImpl(private val context: Context) : PrefDataStore {

    private val Context.dataStore by preferencesDataStore(name = "custom_prefs")

    private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")

    override val preferencesFlow: Flow<Preferences> = context.dataStore.data
        .map { preferences ->
            Preferences(
                isDarkMode = preferences[IS_DARK_MODE] ?: false
            )
        }

    override suspend fun savePreferences(preferences: Preferences) {
        context.dataStore.edit { prefs ->
            prefs[IS_DARK_MODE] = preferences.isDarkMode
        }
    }
}
