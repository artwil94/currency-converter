package com.example.currencyconverter.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.currencyconverter.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int? = null,
    @DrawableRes val icon: Int? = null,
    @StringRes val contentDescription: Int? = null
) {
    companion object {
        private const val ROUTE_HOME = "home"
        const val ROUTE_SEARCH = "search"
    }

    data object Home : Screen(
        route = ROUTE_HOME,
        title = R.string.home,
    )

    data object Search : Screen(
        route = ROUTE_SEARCH,
        title = R.string.search,
    )
}