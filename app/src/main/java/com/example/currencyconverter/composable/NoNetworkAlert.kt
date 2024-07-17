package com.example.currencyconverter.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.theme.TgTheme

@Composable
fun NoNetworkAlert(onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(TgTheme.tGDimensions.padding)
            .shadow(
                elevation = TgTheme.tGDimensions.calculatorItemElevation,
                shape = TgTheme.tGShapes.calculatorItem,
                spotColor = TgTheme.tGColors.shadow
            )
            .background(color = Color.White, shape = TgTheme.tGShapes.inputField),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(TgTheme.tGDimensions.padding)
        ) {
            Icon(
                modifier = Modifier.size(TgTheme.tGDimensions.errorMessage),
                painter = painterResource(id = R.drawable.ic_no_network),
                contentDescription = stringResource(
                    id = R.string.no_network
                ),
                tint = TgTheme.tGColors.inputError
            )
            Spacer(modifier = Modifier.width(TgTheme.tGDimensions.padding))
            Column {
                Text(
                    text = stringResource(id = R.string.no_network),
                    style = TgTheme.tGTypography.noNetwork
                )
                Spacer(modifier = Modifier.height(TgTheme.tGDimensions.paddingL))
                Text(
                    text = stringResource(id = R.string.check_your_internet_connection),
                    style = TgTheme.tGTypography.noNetworkDescription
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .size(TgTheme.tGDimensions.icon)
                    .clickable {
                        onClose.invoke()
                    },
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = stringResource(
                    id = R.string.content_description_close_no_network_dialog
                ),
                tint = TgTheme.tGColors.searchBarBorder
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoNetworkDialogPreview() {
    NoNetworkAlert(onClose = {})
}