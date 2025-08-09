package br.com.maschdy.vwcurrencyconverter.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<HistoryUiState> = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            delay(2000)

            val mockData = listOf(
                "USD/BRL" to "$ 10,00 - R$ 10,00",
                "EUR/BRL" to "$ 20,00 - R$ 20,00"
            )

            _uiState.value = _uiState.value.copy(items = mockData)
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
}

data class HistoryUiState(
    val items: List<Pair<String, String>> = listOf(),
    val errorMessage: String = "",
    val isLoading: Boolean = false
)
