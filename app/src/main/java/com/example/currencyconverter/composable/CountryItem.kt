package com.example.currencyconverter.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R
import com.example.currencyconverter.domain.model.Country
import com.example.currencyconverter.ui.theme.TgTheme


@Composable
fun CountryItem(
    country: Country,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable { onClick.invoke() }
            .padding(
                start = TgTheme.tGDimensions.paddingS,
                end = TgTheme.tGDimensions.paddingS,
                top = TgTheme.tGDimensions.paddingL,
                bottom = TgTheme.tGDimensions.paddingL
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountryIcon(icon = country.icon)
        Spacer(modifier = Modifier.width(TgTheme.tGDimensions.padding))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = country.name,
                style = TgTheme.tGTypography.country
            )
            Spacer(modifier = Modifier.height(TgTheme.tGDimensions.paddingXs))
            Text(
                text = "${country.currencyName} • ${country.currency} ",
                style = TgTheme.tGTypography.calculatorLabel
            )
        }
    }
}

@Composable
fun CountryIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int
) {
    Box(
        modifier = modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(
                TgTheme.tGColors.backgroundScreen
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(0.7f),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountryItemPreview() {
    CountryItem(
        country = Country(
            name = "Poland",
            currency = "PLN",
            currencyName = "Polish zloty",
            sendingLimit = 20000,
            icon = R.drawable.ic_poland_big
        )
    )
}