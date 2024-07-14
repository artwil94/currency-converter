package com.example.currencyconverter.di

import com.example.currencyconverter.data.remote.FxRatesApi
import com.example.currencyconverter.domain.repository.CurrencyRatesRepository
import com.example.currencyconverter.domain.repository.CurrencyRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CurrencyConverterAppModule {

    @Provides
    @Singleton

    fun provideFxRatesApi(): FxRatesApi {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        return Retrofit.Builder()
            .baseUrl("https://my.transfergo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideCurrencyRateRepository(
        fxRatesApi: FxRatesApi,
    ): CurrencyRatesRepository {
        return CurrencyRepositoryImpl(fxRatesApi)
    }
}
