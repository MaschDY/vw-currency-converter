package br.com.maschdy.vwcurrencyconverter.data.network.service

import br.com.maschdy.vwcurrencyconverter.data.network.model.ConvertResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigDecimal

interface ConverterService {
    @GET("/convert")
    suspend fun convertCurrency(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: BigDecimal
    ): Response<ConvertResponse>
}