package com.example.currencyconverter.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface FxRatesApi {

    @GET("api/fx-rates/")
    fun getFxRates(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Float
    ): CurrencyRateDto
}