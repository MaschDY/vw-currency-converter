package br.com.maschdy.vwcurrencyconverter.di

import br.com.maschdy.vwcurrencyconverter.data.network.NetworkFactory.BASE_URL
import br.com.maschdy.vwcurrencyconverter.data.network.NetworkFactory.createService
import br.com.maschdy.vwcurrencyconverter.data.network.NetworkFactory.provideClient
import br.com.maschdy.vwcurrencyconverter.data.network.NetworkFactory.provideMoshi
import br.com.maschdy.vwcurrencyconverter.data.network.NetworkFactory.provideRetrofit
import br.com.maschdy.vwcurrencyconverter.data.network.service.ConverterService
import br.com.maschdy.vwcurrencyconverter.data.repository.ConverterRepositoryImpl
import br.com.maschdy.vwcurrencyconverter.domain.repository.ConverterRepository
import br.com.maschdy.vwcurrencyconverter.presentation.converter.ConverterViewModel
import br.com.maschdy.vwcurrencyconverter.presentation.history.HistoryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import java.net.URL

val baseModules = module {

    single { provideMoshi() }
    single { provideClient() }
    single { provideRetrofit(URL(BASE_URL), get(), get()) }

    single<ConverterService> { createService(get()) }
    single<ConverterRepository> { ConverterRepositoryImpl(get()) }

    viewModelOf(::ConverterViewModel)
    viewModelOf(::HistoryViewModel)
}
