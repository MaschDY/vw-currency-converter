package br.com.maschdy.vwcurrencyconverter.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.maschdy.vwcurrencyconverter.domain.datastore.HistoryDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyDataStore: HistoryDataStore
) : ViewModel() {

    private val _uiState: MutableStateFlow<HistoryUiState> = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            historyDataStore.historyFlow.collectLatest { historyArray ->
                val items = historyArray.map { Pair(it.name, it.value) }
                _uiState.value = _uiState.value.copy(items = items, isLoading = false)
            }
        }
    }
}

data class HistoryUiState(
    val items: List<Pair<String, String>> = listOf(),
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
