package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.domain.model.CurrencyConversion
import com.example.currencyconverter.util.Response

interface CurrencyRepository {

    suspend fun getCurrencyRate(
        from: String,
        to: String,
        amount: Float
    ): Response<CurrencyConversion>
}