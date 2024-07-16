package com.example.currencyconverter.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.theme.TgTheme

@Composable
fun SearchBar(
    text: String,
    onValueChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = TgTheme.tGShapes.inputField)
            .border(
                width = 1.dp,
                color = TgTheme.tGColors.searchBarBorder,
                shape = TgTheme.tGShapes.inputField
            ),
//            .height(48.dp),
        value = text,
        label = {
            Text(
                text = stringResource(id = R.string.search)
            )
        },
        onValueChange = onValueChange,
        textStyle = TgTheme.tGTypography.inputField,
        colors = TextFieldDefaults.colors(
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            disabledContainerColor = Color.White
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

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(text = "")
}