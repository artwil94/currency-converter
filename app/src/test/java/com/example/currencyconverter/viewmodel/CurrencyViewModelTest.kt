package com.example.currencyconverter.viewmodel

import com.example.currencyconverter.domain.model.CurrencyConversion
import com.example.currencyconverter.domain.repository.CurrencyRepository
import com.example.currencyconverter.util.GERMANY_INDEX
import com.example.currencyconverter.util.Response
import com.example.currencyconverter.util.SUPPORTED_COUNTRIES
import com.example.currencyconverter.util.UKRAINE_INDEX
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyViewModelTest {

    private lateinit var repository: CurrencyRepository
    private lateinit var viewModel: CurrencyViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = CurrencyViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCurrencyRates success updates uiState correctly`() = runTest {
        val conversion = CurrencyConversion(
            from = "PLN",
            to = "UAH",
            fromAmount = 300f,
            rate = 10.52,
            toAmount = 3156f
        )
        coEvery { repository.getCurrencyRate("PLN", "UAH", 300f) } returns Response.Success(
            conversion
        )

        assertNull(viewModel.uiState.currencyConversion)
        assertFalse(viewModel.uiState.isLoading)
        assertNull(viewModel.uiState.error)

        viewModel.getCurrencyRates("PLN", "UAH", 300f)
        advanceUntilIdle()

        assertEquals(viewModel.uiState.currencyConversion, conversion)
        assertFalse(viewModel.uiState.isLoading)
        assertNull(viewModel.uiState.error)
    }

    @Test
    fun `getCurrencyRates error updates uiState correctly`() = runTest {
        val errorConversion = CurrencyConversion(
            from = "PLN",
            to = "UAH",
            fromAmount = 0f,
            rate = 0.0,
            toAmount = 0f
        )
        coEvery { repository.getCurrencyRate("PLN", "UAH", 300f) } returns Response.Error(
            errorConversion,
            "API response error"
        )

        assertNull(viewModel.uiState.currencyConversion)
        assertFalse(viewModel.uiState.isLoading)
        assertNull(viewModel.uiState.error)

        viewModel.getCurrencyRates("PLN", "UAH", 300f)

        advanceUntilIdle()

        assertNull(viewModel.uiState.currencyConversion)
        assertFalse(viewModel.uiState.isLoading)
        assertEquals(viewModel.uiState.error, "API response error")
    }

    @Test
    fun `updateCountry updates fromCountry and fetches rates`() = runTest {
        val country = SUPPORTED_COUNTRIES[GERMANY_INDEX]
        val conversion = CurrencyConversion(
            from = "EUR",
            to = "PLN",
            fromAmount = 100f,
            rate = 4.3,
            toAmount = 340f
        )
        coEvery { repository.getCurrencyRate(any(), any(), any()) } returns Response.Success(
            conversion
        )

        viewModel.updateCountry(country, true)

        advanceUntilIdle()

        assertEquals(viewModel.uiState.fromCountry, country)
        coVerify {
            repository.getCurrencyRate(
                country.currency,
                viewModel.uiState.toCountry.currency,
                0.0f
            )
        }
    }

    @Test
    fun `updateCountry updates toCountry and fetches rates`() = runTest {
        val country = SUPPORTED_COUNTRIES[UKRAINE_INDEX]
        val conversion = CurrencyConversion(
            from = "PLN",
            to = "UAH",
            fromAmount = 300f,
            rate = 10.52,
            toAmount = 75f
        )
        coEvery { repository.getCurrencyRate(any(), any(), any()) } returns Response.Success(
            conversion
        )

        viewModel.updateCountry(country, false)

        advanceUntilIdle()

        assertEquals(viewModel.uiState.toCountry, country)
        coVerify {
            repository.getCurrencyRate(
                viewModel.uiState.fromCountry.currency,
                country.currency,
                0.0f
            )
        }
    }

    @Test
    fun `searchCountry filters countries correctly for Germany and Great Britain`() = runTest {
        viewModel.searchCountry("g")

        advanceUntilIdle()

        val expectedCountries =
            SUPPORTED_COUNTRIES.filter { it.name.contains("g", ignoreCase = true) }

        assertEquals(expectedCountries, viewModel.uiState.countriesToDisplay)
    }

    @Test
    fun `searchCountry with blank input shows all countries`() = runTest {
        viewModel.searchCountry("")

        advanceUntilIdle()

        assertEquals(viewModel.uiState.countriesToDisplay, SUPPORTED_COUNTRIES)
    }

    @Test
    fun `switchCurrencies swaps fromCountry and toCountry`() = runTest {
        val initialFromCountry = viewModel.uiState.fromCountry
        val initialToCountry = viewModel.uiState.toCountry

        coEvery {
            repository.getCurrencyRate(
                initialFromCountry.currency,
                initialToCountry.currency,
                any()
            )
        } returns Response.Success(
            CurrencyConversion(
                from = initialFromCountry.currency,
                to = initialToCountry.currency,
                fromAmount = 10f,
                rate = 2.0,
                toAmount = 20f
            )
        )

        viewModel.getCurrencyRates(
            from = initialFromCountry.currency,
            to = initialToCountry.currency,
            amount = 10f
        )
        advanceUntilIdle()
        coEvery {
            repository.getCurrencyRate(
                initialToCountry.currency,
                initialFromCountry.currency,
                any()
            )
        } returns Response.Success(
            CurrencyConversion(
                from = initialToCountry.currency,
                to = initialFromCountry.currency,
                fromAmount = 10f,
                rate = 2.0,
                toAmount = 20f
            )
        )
        viewModel.switchCurrencies()

        advanceUntilIdle()

        assertEquals(viewModel.uiState.fromCountry, initialToCountry)
        assertEquals(viewModel.uiState.toCountry, initialFromCountry)
        coVerify {
            repository.getCurrencyRate(
                initialToCountry.currency,
                initialFromCountry.currency,
                viewModel.uiState.currencyConversion?.fromAmount!!
            )
        }
    }
}
