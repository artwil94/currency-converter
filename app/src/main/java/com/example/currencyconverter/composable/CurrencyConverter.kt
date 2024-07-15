package com.example.currencyconverter.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R
import com.example.currencyconverter.domain.model.CurrencyRate
import com.example.currencyconverter.ui.theme.TgTheme

@Composable
fun CalculatorItem(
    modifier: Modifier = Modifier,
    currencyRate: CurrencyRate,
    itemType: ItemType,
    error: Boolean = false,
    @DrawableRes flag: Int
) {
    Box(
        modifier = modifier
            .height(TgTheme.tGDimensions.calculatorItemHeight)
            .background(
                if (itemType == ItemType.Receiver) Color.LightGray
                else Color.White, shape = TgTheme.tGShapes.calculatorItem
            ),
////            .border(
//                width = if (error) 2.dp else 0.dp,
//                shape = TgTheme.tGShapes.calculatorItem,
//                color = Color.Red
//            ),
        contentAlignment = Alignment.Center,
    ) {
        var currency by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        val fromAmount = currencyRate.fromAmount
        val toAmount = currencyRate.toAmount
        val fromAmountFormatted = String.format("%.2f", fromAmount.toDouble())
        val toAmountFormatted = String.format("%.2f", toAmount)

        when (itemType) {
            ItemType.Sending -> {
                currency = currencyRate.from
                amount = fromAmountFormatted
            }

            ItemType.Receiver -> {
                currency = currencyRate.to
                amount = toAmountFormatted
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(TgTheme.tGDimensions.paddingL),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Sending from", style = TgTheme.tGTypography.calculatorLabel)
                Spacer(modifier = Modifier.height(TgTheme.tGDimensions.padding))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(TgTheme.tGDimensions.paddingS),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(TgTheme.tGDimensions.icon),
                        painter = painterResource(id = flag),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = currency,
                        style = TgTheme.tGTypography.currency
                    )
                    Icon(
                        modifier = Modifier.size(TgTheme.tGDimensions.iconSmall),
                        painter = painterResource(id = R.drawable.ic_chevron_down),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = amount,
                style = if (itemType == ItemType.Sending) TgTheme.tGTypography.fromAmount
                else TgTheme.tGTypography.toAmount
            )
        }
    }
}

@Composable
fun CurrencyConverter(error: Boolean = false) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(184.dp)
            .padding(TgTheme.tGDimensions.paddingS)
            .shadow(
                elevation = TgTheme.tGDimensions.calculatorItemElevation,
                shape = TgTheme.tGShapes.calculatorItem,
                spotColor = TgTheme.tGColors.shadow
            )
            .background(Color.LightGray, shape = TgTheme.tGShapes.calculatorItem)
    ) {
        Column {
            CalculatorItem(
                currencyRate = CurrencyRate(
                    from = "PLN",
                    to = "UAH",
                    rate = 3.90,
                    fromAmount = 100,
                    toAmount = 390.00
                ),
                flag = R.drawable.ic_poland_s,
                itemType = ItemType.Sending,
                error = error
            )
            CalculatorItem(
                currencyRate = CurrencyRate(
                    from = "PLN",
                    to = "UAH",
                    rate = 3.90,
                    fromAmount = 100,
                    toAmount = 390.00
                ),
                flag = R.drawable.ic_ukraine_s,
                itemType = ItemType.Receiver
            )
        }
    }
}

enum class ItemType {
    Sending,
    Receiver
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun CalculatorItemPreview() {
    CalculatorItem(
        currencyRate = CurrencyRate(
            from = "PLN",
            to = "UAH",
            rate = 3.90,
            fromAmount = 100,
            toAmount = 390.00
        ),
        flag = R.drawable.ic_poland_s,
        itemType = ItemType.Sending
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun CurrencyConverterPreview() {
    CurrencyConverter()
}