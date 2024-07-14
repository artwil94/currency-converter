package com.example.currencyconverter.domain.model

import com.example.currencyconverter.data.remote.CurrencyRateDto

data class CurrencyRate(
    val from: String,
    val to: String,
    val rate: Double,
    val fromAmount: Int,
    val toAmount: Double
)

fun CurrencyRateDto.toCurrencyRate(): CurrencyRate? {
    return if (from != null && to != null && rate != null && fromAmount != null && toAmount != null) {
        CurrencyRate(
            from = from,
            to = to,
            rate = rate,
            fromAmount = fromAmount,
            toAmount = toAmount
        )
    } else {
        null
    }
}