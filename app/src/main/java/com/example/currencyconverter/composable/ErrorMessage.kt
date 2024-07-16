package com.example.currencyconverter.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverter.R
import com.example.currencyconverter.domain.model.Country
import com.example.currencyconverter.ui.theme.TgTheme

@Composable
fun ErrorMessage(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(TgTheme.tGDimensions.errorMessage)
            .background(
                color = TgTheme.tGColors.errorMessageContainer,
                shape = TgTheme.tGShapes.inputField
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TgTheme.tGDimensions.paddingS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(TgTheme.tGDimensions.iconSmall),
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = stringResource(
                    id = R.string.maximum_sending_amount
                ),
                tint = TgTheme.tGColors.errorMessage
            )
            Spacer(modifier = Modifier.width(TgTheme.tGDimensions.paddingS))
            Text(
//                text = stringResource(id = R.string.maximum_sending_amount) + ": " + "${country.sendingLimit} ${country.currency} ",
                text = text,
                style = TgTheme.tGTypography.errorMesage
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun ErrorMessagePreview() {
    val country = Country(
        name = "Ukraine",
        currency = "UAH",
        currencyName = "Hrivna",
        sendingLimit = 50000f,
        icon = R.drawable.ic_ukraine_big
    )
    ErrorMessage(
        text = stringResource(id = R.string.maximum_sending_amount) + ": " + "${country.sendingLimit} ${country.currency} "
    )
}
