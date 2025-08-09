package br.com.maschdy.vwcurrencyconverter.domain.model

enum class Currency {
    BRL,
    USD,
    EUR,
    CAD,
    JPY
}

val currencyCodes = Currency.entries.map { it.name }
