package com.example.currencyconverter.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.currencyconverter.R

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun CurrencyConverterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

object TgTheme {
    val tGTypography: TgTypography
        @Composable get() = TgTypography()

    val tGShapes: TgShapes
        @Composable get() = TgShapes()
    val fonts: Fonts = Fonts()
    val tGDimensions: TgDimensions
        @Composable get() = TgDimensions()
    val tGColors: TgColors
        @Composable get() = TgColors()
}

data class TgTypography(
    val inputField: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = TgTheme.fonts.robotoLight,
        fontWeight = FontWeight(300),
        color = Color(0xFF34303D),
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        ),
    ),
    val labelSmall: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight(300),
        color = Color(0xFF696878),
        fontFamily = TgTheme.fonts.robotoLight
    ),
    val bottomSheetTitle: TextStyle = TextStyle(
        fontSize = 28.sp,
        lineHeight = 32.sp,
        fontFamily = TgTheme.fonts.robotoBold,
        fontWeight = FontWeight(700),
        color = Color.Black,
    ),
    val toolbar: TextStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight(550),
        color = Color(0xFF333333),
        fontFamily = TgTheme.fonts.freigeistMedium
    ),
    val currency: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = TgTheme.fonts.freigeistMedium,
        fontWeight = FontWeight(550),
        color = Color(0xFF333333),
    ),
    val calculatorLabel: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = TgTheme.fonts.robotoLight,
        fontWeight = FontWeight(300),
        color = Color(0xFF696878)
    ),
    val rateComponent: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = TgTheme.fonts.robotoMedium,
        fontWeight = FontWeight(400),
        color = Color.White
    ),
    val fromAmount: TextStyle = TextStyle(
        fontSize = 40.sp,
        lineHeight = 40.sp,
        fontFamily = TgTheme.fonts.freigeistMedium,
        fontWeight = FontWeight(550),
        color = Color(0xFF2962FF),
        textAlign = TextAlign.End
    ),
    val fromAmountError: TextStyle = TextStyle(
        fontSize = 40.sp,
        lineHeight = 40.sp,
        fontFamily = TgTheme.fonts.freigeistMedium,
        fontWeight = FontWeight(550),
        color = Color(0xFFF8326A),
        textAlign = TextAlign.End
    ),
    val toAmount: TextStyle = TextStyle(
        fontSize = 40.sp,
        lineHeight = 40.sp,
        fontFamily = TgTheme.fonts.freigeistMedium,
        fontWeight = FontWeight(550),
        color = Color.Black,
        textAlign = TextAlign.End
    ),
    val distance: TextStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = 24.sp,
        fontFamily = TgTheme.fonts.freigeistMedium,
        fontWeight = FontWeight(550),
        color = Color(0xFF333333),
    ),
    val bottomSheetSubtitle: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 24.sp,
        fontFamily = TgTheme.fonts.robotoMedium,
        fontWeight = FontWeight(550),
        color = Color.Black,
    ),
    val country: TextStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontFamily = TgTheme.fonts.freigeistMedium,
        fontWeight = FontWeight(550),
        color = Color.Black
    ),
    val errorMesage: TextStyle = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = TgTheme.fonts.robotoMedium,
        fontWeight = FontWeight(400),
        color = Color(0xFFE5476D)
    ),
)

data class Fonts(
    val freigeistMedium: FontFamily = FontFamily(Font(R.font.freigeist_xconmedium)),
    val freigeistBold: FontFamily = FontFamily(Font(R.font.freigeist_xconbold)),
    val robotoMedium: FontFamily = FontFamily(Font(R.font.roboto_medium)),
    val robotoBold: FontFamily = FontFamily(Font(R.font.roboto_bold)),
    val robotoLight: FontFamily = FontFamily(Font(R.font.roboto_light)),
    val robotoRegular: FontFamily = FontFamily(Font(R.font.roboto_regular))
)

data class TgShapes(
    val inputField: Shape = RoundedCornerShape(10.dp),
    val calculatorItem: Shape = RoundedCornerShape(16.dp),
    val calculatorItemDownRounded: Shape = RoundedCornerShape(
        bottomStart = 16.dp,
        bottomEnd = 16.dp
    ),
    val buttonDefaultShape: Shape = RoundedCornerShape(size = 1000.dp),
    val bottomSheet: Shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
    val currencyRateComponent: Shape = RoundedCornerShape(50.dp),
    val errorComponent: Shape = RoundedCornerShape(8.dp),
)

data class TgDimensions(
    val paddingXs: Dp = 4.dp,
    val paddingS: Dp = 8.dp,
    val paddingL: Dp = 12.dp,
    val padding: Dp = 16.dp,
    val paddingMedium: Dp = 20.dp,
    val paddingXL: Dp = 24.dp,
    val paddingXXL: Dp = 30.dp,
    val paddingSeparator: Dp = 50.dp,
    val calculatorItemElevation: Dp = 20.dp,
    val inputFieldHeight: Dp = 50.dp,
    val icon: Dp = 24.dp,
    val iconSmall: Dp = 16.dp,
    val searchBarCloseIcon: Dp = 20.dp,
    val calculatorItemReceiverHeight: Dp = 100.dp,
    val bottomSheetHandle: Dp = 6.dp,
    val countryIcon: Dp = 42.dp,
    val errorMessage: Dp = 32.dp
)

data class TgColors(
    val textStandard: Color = Color(0xFF333333),
    val shadow: Color = Color(0x0D000000),
    val backgroundScreen: Color = Color(0xFFEDF0F4),
    val bottomSheetHandle: Color = Color(0xFFE6E1E6),
    val surface: Color = bottomSheetHandle,
    val searchBarBorder: Color = Color(0xFF6C727A),
    val switchIcon: Color = Color(0xFF2962FF),
    val inputError: Color = Color(0xFFF8326A),
    val errorMessageContainer: Color = Color(0x1AE5476D),
    val errorMessage: Color = Color(0xFFE5476D),

    )