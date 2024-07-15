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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.currencyconverter.BottomSheetHandle
import com.example.currencyconverter.ui.theme.TgTheme

@ExperimentalMaterialApi
@Composable
fun SearchBottomSheet(
    title: String,
    infoMessage: String,
    bottomSheetState: ModalBottomSheetState
) {
    val coroutine = rememberCoroutineScope()
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

            }
        }
    ) {
    }
}