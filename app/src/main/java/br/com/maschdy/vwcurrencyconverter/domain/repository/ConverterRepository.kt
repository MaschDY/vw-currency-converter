package br.com.maschdy.vwcurrencyconverter.domain.repository

import br.com.maschdy.vwcurrencyconverter.data.network.model.ConvertResponse
import br.com.maschdy.vwcurrencyconverter.domain.model.Result
import java.math.BigDecimal

interface ConverterRepository {
    suspend fun convertCurrency(from: String, to: String, value: BigDecimal): Result<ConvertResponse>
}
