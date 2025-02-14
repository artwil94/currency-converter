package com.example.currencyconverter.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R
import com.example.currencyconverter.domain.model.Country
import com.example.currencyconverter.ui.theme.TgTheme
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun SearchBottomSheet(
    title: String,
    countries: List<Country>,
    bottomSheetState: ModalBottomSheetState,
    onCountry: (Country, Boolean) -> Unit,
    onSearchInputChange: (String) -> Unit,
    isFromCountry: Boolean
) {
    val coroutine = rememberCoroutineScope()
    var text by remember { mutableStateOf<String?>(null) }
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = TgTheme.tGShapes.bottomSheet,
        sheetContent = {
            Column(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(
                        start = TgTheme.tGDimensions.padding,
                        end = TgTheme.tGDimensions.padding
                    )
                    .height(800.dp)
            ) {
                Spacer(modifier = Modifier.height(TgTheme.tGDimensions.bottomSheetHandle))
                BottomSheetHandle()
                Spacer(modifier = Modifier.height(TgTheme.tGDimensions.paddingXL))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = title,
                    style = TgTheme.tGTypography.bottomSheetTitle
                )
                Spacer(modifier = Modifier.height(TgTheme.tGDimensions.paddingXL))
                SearchBar(text = text ?: "", onValueChange = { value ->
                    text = value
                    onSearchInputChange.invoke(value)
                })
                Spacer(modifier = Modifier.height(TgTheme.tGDimensions.paddingXXL))
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(id = R.string.all_countries),
                    style = TgTheme.tGTypography.bottomSheetSubtitle
                )
                Spacer(modifier = Modifier.height(TgTheme.tGDimensions.paddingXL))
                countries.forEach { country ->
                    CountryItem(country = country, onClick = {
                        onCountry.invoke(country, isFromCountry)
                        coroutine.launch {
                            bottomSheetState.hide()
                        }
                        text = null
                    })
                    HorizontalDivider(color = TgTheme.tGColors.backgroundScreen)
                    Spacer(modifier = Modifier.height(TgTheme.tGDimensions.paddingMedium))
                }
            }
        }
    ) {
    }
}