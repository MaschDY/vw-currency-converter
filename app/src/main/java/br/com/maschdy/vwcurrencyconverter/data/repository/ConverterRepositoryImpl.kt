package br.com.maschdy.vwcurrencyconverter.data.repository

import br.com.maschdy.vwcurrencyconverter.data.network.NetworkHandler
import br.com.maschdy.vwcurrencyconverter.data.network.model.ConvertResponse
import br.com.maschdy.vwcurrencyconverter.data.network.service.ConverterService
import br.com.maschdy.vwcurrencyconverter.domain.model.Result
import br.com.maschdy.vwcurrencyconverter.domain.model.Success
import br.com.maschdy.vwcurrencyconverter.domain.repository.ConverterRepository
import java.math.BigDecimal

class ConverterRepositoryImpl(
    private val service: ConverterService
) : ConverterRepository {
    override suspend fun convertCurrency(
        from: String,
        to: String,
        value: BigDecimal
    ): Result<ConvertResponse> {
        val response = NetworkHandler.handle { service.convertCurrency(from, to, value) }
        return if (response is Success) {
            Success(response.data)
        } else {
            response
        }
    }
}
