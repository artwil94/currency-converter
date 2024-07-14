package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.CurrencyRate
import com.example.currencyconverter.util.Response

interface CurrencyRatesRepository {

    suspend fun getCurrencyRates(
        from: String,
        to: String,
        amount: Float
    ): Response<CurrencyRate>
}