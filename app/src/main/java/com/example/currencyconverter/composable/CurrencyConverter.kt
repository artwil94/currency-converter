package com.example.currencyconverter.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R
import com.example.currencyconverter.domain.model.CurrencyConversion
import com.example.currencyconverter.ui.theme.TgTheme

@Composable
fun CalculatorItem(
    modifier: Modifier = Modifier,
    fromAmount: String = "",
    currencyConversion: CurrencyConversion,
    itemType: ItemType,
    error: Boolean = false,
    onChevronDownClick: () -> Unit = {},
    onSendingAmountChange: (String) -> Unit = {},
    onDone: () -> Unit = {},
    height: Dp = TgTheme.tGDimensions.calculatorItemReceiverHeight,
    @DrawableRes flag: Int
) {
    Box(
        modifier = modifier
            .height(height)
            .background(
                if (itemType == ItemType.Receiver) TgTheme.tGColors.surface
                else Color.White, shape = TgTheme.tGShapes.calculatorItem
            )
            .border(
                width = 2.dp,
                shape = TgTheme.tGShapes.calculatorItem,
                color = if (error) TgTheme.tGColors.inputError else Color.Transparent
            ),
        contentAlignment = Alignment.Center,
    ) {
        var currency by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var itemLabel by remember { mutableStateOf("") }
        var sendingAmountTextStyle by remember { mutableStateOf<TextStyle?>(null) }
        val toAmount = currencyConversion.toAmount
//        val fromAmountFormatted = String.format("%.2f", fromAmount.toDouble())
        val toAmountFormatted = String.format("%.2f", toAmount)

        when (itemType) {
            ItemType.Sending -> {
                currency = currencyConversion.from
                amount = fromAmount
                itemLabel = stringResource(id = R.string.sending_from)
                sendingAmountTextStyle =
                    if (error) TgTheme.tGTypography.fromAmountError
                    else TgTheme.tGTypography.fromAmount
            }

            ItemType.Receiver -> {
                currency = currencyConversion.to
                amount = toAmountFormatted
                itemLabel = stringResource(id = R.string.receiver_gets)
                sendingAmountTextStyle = TgTheme.tGTypography.toAmount
            }
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(TgTheme.tGDimensions.paddingL),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = itemLabel, style = TgTheme.tGTypography.calculatorLabel)
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
                        modifier = Modifier
                            .size(TgTheme.tGDimensions.iconSmall)
                            .clickable {
                                onChevronDownClick.invoke()
                            },
                        painter = painterResource(id = R.drawable.ic_chevron_down),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
            InputField(
                text = amount,
                textStyle = sendingAmountTextStyle!!,
                onValueChange = onSendingAmountChange,
                enabled = itemType == ItemType.Sending,
                onDone = onDone
            )
        }
    }
}

@Composable
fun CurrencyConverter(
    currencyConversion: CurrencyConversion,
    fromAmount: String = "",
    error: Boolean = false,
    onChevronDownClick: () -> Unit = {},
    onSendingAmountChange: (String) -> Unit = {},
    onDone: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(TgTheme.tGDimensions.paddingS)
            .shadow(
                elevation = TgTheme.tGDimensions.calculatorItemElevation,
                shape = TgTheme.tGShapes.calculatorItem,
                spotColor = TgTheme.tGColors.shadow
            )
            .background(
                color = TgTheme.tGColors.surface,
                shape = TgTheme.tGShapes.calculatorItem
            ),
        contentAlignment = Alignment.Center
    ) {
        Column {
            CalculatorItem(
                currencyConversion = currencyConversion,
                fromAmount = fromAmount,
                flag = R.drawable.ic_poland_s,
                itemType = ItemType.Sending,
                error = error,
                onChevronDownClick = onChevronDownClick,
                height = 92.dp,
                onSendingAmountChange = onSendingAmountChange,
                onDone = onDone
            )
            CalculatorItem(
                currencyConversion = currencyConversion,
                flag = R.drawable.ic_ukraine_s,
                itemType = ItemType.Receiver,
                onChevronDownClick = onChevronDownClick
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(0.5f))
            SwitchIcon()
            Spacer(modifier = Modifier.weight(1f))
            CurrencyRateComponent(currencyConversion = currencyConversion)
            Spacer(modifier = Modifier.weight(2f))
        }
    }
}

@Composable
fun CurrencyRateComponent(currencyConversion: CurrencyConversion) {
    Box(
        modifier = Modifier
            .background(
                color = Color.Black,
                shape = TgTheme.tGShapes.currencyRateComponent
            )
            .clip(TgTheme.tGShapes.currencyRateComponent)
            .padding(
                start = TgTheme.tGDimensions.paddingS,
                top = TgTheme.tGDimensions.paddingXs,
                end = TgTheme.tGDimensions.paddingS,
                bottom = TgTheme.tGDimensions.paddingXs
            )
    ) {
        val rateFormatted = String.format("%.2f", currencyConversion.rate)
        Text(
            text = "1 ${currencyConversion.from} = $rateFormatted ${currencyConversion.to}",
            color = Color.White,
            style = TgTheme.tGTypography.rateComponent
        )
    }
}

@Composable
fun SwitchIcon(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(TgTheme.tGDimensions.paddingXXL)
            .clip(CircleShape)
            .background(
                TgTheme.tGColors.switchIcon
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(0.8f),
            painter = painterResource(id = R.drawable.ic_up_down_arrows),
            contentDescription = null,
            tint = Color.White
        )
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
        currencyConversion = CurrencyConversion(
            from = "PLN",
            to = "UAH",
            rate = 3.90,
            fromAmount = 100.00,
            toAmount = 390.00
        ),
        flag = R.drawable.ic_poland_s,
        itemType = ItemType.Sending
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun CurrencyConverterPreview() {
    CurrencyConverter(
        currencyConversion = CurrencyConversion(
            from = "PLN",
            to = "UAH",
            rate = 3.90,
            fromAmount = 100.00,
            toAmount = 390.00
        ),
        error = true
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun CurrencyRateComponentPreview() {
    CurrencyRateComponent(
        currencyConversion = CurrencyConversion(
            from = "PLN",
            to = "UAH",
            rate = 3.90,
            fromAmount = 100.00,
            toAmount = 390.00
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SwitchIconPreview() {
    SwitchIcon()
}