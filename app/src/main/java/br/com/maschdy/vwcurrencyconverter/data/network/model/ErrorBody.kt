package br.com.maschdy.vwcurrencyconverter.data.network.model

import com.squareup.moshi.Json

data class ErrorBody(
    @Json(name = "success") val isSuccess: Boolean,
    @Json(name = "error") val error: ErrorDetails? = null
)

data class ErrorDetails(
    @Json(name = "code") val code: Int,
    @Json(name = "type") val type: String,
    @Json(name = "info") val info: String
)
