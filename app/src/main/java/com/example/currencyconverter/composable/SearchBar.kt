package com.example.currencyconverter.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.theme.TgTheme

@ExperimentalMaterial3Api
@Composable
fun SearchBar(
    text: String,
    onValueChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = TgTheme.tGShapes.inputField),
        shape = TgTheme.tGShapes.inputField,
        value = text,
        label = {
            Text(
                text = stringResource(id = R.string.search),
                style = TgTheme.tGTypography.searchBarLabel
            )
        },
        onValueChange = onValueChange,
        textStyle = TgTheme.tGTypography.inputField,
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = TgTheme.tGColors.searchBarBorder,
            focusedBorderColor = TgTheme.tGColors.searchBarBorder,
            unfocusedBorderColor = TgTheme.tGColors.searchBarBorder,
            unfocusedLabelColor = TgTheme.tGColors.searchBarBorder,
            focusedLabelColor = TgTheme.tGColors.searchBarBorder,
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.content_description_search_countries),
                tint = TgTheme.tGColors.searchBarBorder
            )
        }
    )
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(text = "")
}