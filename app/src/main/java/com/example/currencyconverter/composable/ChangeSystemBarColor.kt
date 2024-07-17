package com.example.currencyconverter.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ChangeSystemBarColor(
    statusBarColor: Color = Color.Transparent,
    navigationBarColor: Color = Color.Transparent
) {
    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(color = statusBarColor)
    systemUiController.setNavigationBarColor(color = navigationBarColor)
}