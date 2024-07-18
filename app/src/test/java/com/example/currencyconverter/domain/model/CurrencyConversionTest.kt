package com.example.currencyconverter.domain.model

import com.example.currencyconverter.data.remote.CurrencyConversionDto
import org.junit.Assert
import org.junit.Test

class CurrencyConversionTest {

    @Test
    fun `test toCurrencyConversion validData validObject`() {
        val input = CurrencyConversionDto(
            from= "GBP",
            to = "PLN",
            rate  = 1.2,
            fromAmount = 300f,
            toAmount = 360f
        )

        val result = input.toCurrencyConversion()

        Assert.assertNotNull(result)
        Assert.assertEquals(input.from, result?.from)
        Assert.assertEquals(input.to, result?.to)
        Assert.assertEquals(input.rate, result?.rate)
        Assert.assertEquals(input.fromAmount, result?.fromAmount)
        Assert.assertEquals(input.toAmount, result?.toAmount)
    }

    @Test
    fun `test toCurrencyConversion missing from null`() {
        val input = CurrencyConversionDto(
            to = "PLN",
            rate  = 1.2,
            fromAmount = 300f,
            toAmount = 360f
        )
        val result = input.toCurrencyConversion()

        Assert.assertNull(result)
    }

    @Test
    fun `test toCurrencyConversion missing to null`() {
        val input = CurrencyConversionDto(
            from= "GBP",
            rate  = 1.2,
            fromAmount = 300f,
            toAmount = 360f
        )
        val result = input.toCurrencyConversion()

        Assert.assertNull(result)
    }
}