package br.com.maschdy.vwcurrencyconverter.domain.datastore

import br.com.maschdy.vwcurrencyconverter.domain.model.Preferences
import kotlinx.coroutines.flow.Flow

interface PrefDataStore {
    val preferencesFlow: Flow<Preferences>
    suspend fun savePreferences(preferences: Preferences)
}
