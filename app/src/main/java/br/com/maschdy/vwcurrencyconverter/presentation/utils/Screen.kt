package br.com.maschdy.vwcurrencyconverter.presentation.utils

sealed class Screen(val route: String) {
    object ConverterScreen : Screen("converter_screen")
    object HistoryScreen : Screen("history_screen")
}