package br.com.maschdy.vwcurrencyconverter.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URL
import br.com.maschdy.vwcurrencyconverter.BuildConfig

object NetworkFactory {
    const val BASE_URL = "https://api.exchangerate.host"

    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    fun provideClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().setLevel(Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor(BuildConfig.EXCHANGERATE_API_KEY))
            .addInterceptor(logging)
            .build()
    }

    fun provideRetrofit(baseUrl: URL, moshi: Moshi, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    inline fun <reified T> createService(retrofit: Retrofit): T = retrofit.create(T::class.java)
}
