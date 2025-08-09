package br.com.maschdy.vwcurrencyconverter

import android.app.Application
import br.com.maschdy.vwcurrencyconverter.di.baseModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.loadKoinModules

class VWCurrencyConverterApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startDI()
    }

    private fun startDI() {
        startKoin {
            androidLogger()
            androidContext(this@VWCurrencyConverterApp)
            loadKoinModules(baseModules)
        }
    }
}
