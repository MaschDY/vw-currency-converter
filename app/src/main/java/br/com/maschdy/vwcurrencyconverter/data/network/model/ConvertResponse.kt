package br.com.maschdy.vwcurrencyconverter.data.network.model

data class ConvertResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val query: Query,
    val info: Info,
    val result: Double
)

data class Query(
    val from: String,
    val to: String,
    val amount: Double
)

data class Info(
    val timestamp: Long,
    val quote: Double
)
