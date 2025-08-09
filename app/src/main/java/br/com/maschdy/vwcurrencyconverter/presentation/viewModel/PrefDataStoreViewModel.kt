package br.com.maschdy.vwcurrencyconverter.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.maschdy.vwcurrencyconverter.domain.datastore.PrefDataStore
import br.com.maschdy.vwcurrencyconverter.domain.model.Preferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PrefDataStoreViewModel(
    private val prefDataStore: PrefDataStore
) : ViewModel() {

    private val _preferences = MutableStateFlow(Preferences())
    val preferences: StateFlow<Preferences> = _preferences

    init {
        viewModelScope.launch {
            prefDataStore.preferencesFlow.collect { prefs ->
                _preferences.value = prefs
            }
        }
    }

    fun setDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            prefDataStore.savePreferences(Preferences(isDarkMode))
        }
    }
}
