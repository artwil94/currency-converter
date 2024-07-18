package com.example.currencyconverter

import com.example.currencyconverter.util.convertAmount
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun `test covert amount`() {
        assertEquals("123.45", convertAmount(123.45f))
        assertEquals("0.00", convertAmount(0.0f))
        assertEquals("1000.00", convertAmount(1000.0f))
        assertEquals("1.23", convertAmount(1.2345f))
        assertEquals("1.24", convertAmount(1.235f))
        assertEquals("1.24", convertAmount(1.236f))
        assertEquals("-123.45", convertAmount(-123.45f))
    }
}