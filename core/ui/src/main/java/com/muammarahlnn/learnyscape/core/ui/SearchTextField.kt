package com.muammarahlnn.learnyscape.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchTextField, 13/11/2023 16.31 by Muammar Ahlan Abimanyu
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    searchQuery: String,
    placeholderText: String,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = searchQuery,
        onValueChange = {
            if (!it.contains("\n")) {
                onSearchQueryChanged(it)
            }
        },
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.surfaceVariant,
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = designSystemR.drawable.ic_search),
                contentDescription = stringResource(id = designSystemR.string.search_icon_description),
                tint = MaterialTheme.colorScheme.surfaceVariant
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onSearchQueryChanged("")
                    },
                ) {
                    Icon(
                        painter = painterResource(id =designSystemR.drawable.ic_close),
                        contentDescription = stringResource(id = designSystemR.string.clear_icon_description),
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
            },
        ),
        maxLines = 1,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodySmall,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.outline,
            focusedContainerColor = MaterialTheme.colorScheme.outline,
            disabledContainerColor = MaterialTheme.colorScheme.outline,
            unfocusedTextColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = modifier
            .fillMaxWidth()
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    keyboardController?.hide()
                    true
                } else {
                    false
                }
            },
    )
}