package com.example.currencyconverter.domain.repository

import com.example.currencyconverter.data.remote.FxRatesApi
import com.example.currencyconverter.domain.model.CurrencyConversion
import com.example.currencyconverter.domain.model.toCurrencyConversion
import com.example.currencyconverter.util.Response
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val fxRatesApi: FxRatesApi
) : CurrencyRepository {
    override suspend fun getCurrencyRate(
        from: String,
        to: String,
        amount: Float
    ): Response<CurrencyConversion> {
        return try {
            val result =
                fxRatesApi.getFxRates(from = from, to = to, amount = amount).toCurrencyConversion()
            Response.Success(
                data = result
            )
        } catch (e: Exception) {
            Response.Error(message = e.message)
        }
    }
}