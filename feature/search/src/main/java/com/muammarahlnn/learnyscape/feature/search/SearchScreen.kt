package com.muammarahlnn.learnyscape.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppbarDefaults
import com.muammarahlnn.learnyscape.core.ui.ClassCard
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchScreen, 20/07/2023 22.06 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun SearchRoute(
    onPendingClassRequestClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showJoinRequestDialog by rememberSaveable {
        mutableStateOf(false)
    }

    SearchScreen(
        showJoinRequestDialog = showJoinRequestDialog,
        onClassItemClick = {
            showJoinRequestDialog = true
        },
        onDismissJoinRequestDialog = {
            showJoinRequestDialog = false
        },
        onPendingClassRequestClick = onPendingClassRequestClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    showJoinRequestDialog: Boolean,
    onClassItemClick: () -> Unit,
    onDismissJoinRequestDialog: () -> Unit,
    onPendingClassRequestClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (showJoinRequestDialog) {
        JoinRequestClassDialog(
            onDismiss = onDismissJoinRequestDialog,
        )
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column(modifier = modifier.fillMaxSize()) {
        SearchTopAppBar(
            scrollBehavior = scrollBehavior,
            onPendingClassRequestClick = onPendingClassRequestClick,
        )

        SearchTextField()

        SearchResult(
            scrollBehavior = scrollBehavior,
            onClassItemClick = onClassItemClick,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopAppBar(
    onPendingClassRequestClick: () -> Unit,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    LearnyscapeTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.search)
            )
        },
        actionIconRes = R.drawable.ic_hourglass,
        actionIconContentDescription = stringResource(
            id = R.string.pending_request_icon_description
        ),
        onActionClick = onPendingClassRequestClick,
        colors = LearnyscapeTopAppbarDefaults.defaultTopAppBarColors(),
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchTextField() {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
    }

    var searchQuery by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = searchQuery,
        onValueChange = {
            searchQuery = it
        },
        placeholder = {
            Text(text = stringResource(id = R.string.search_text_field_placeholder))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search_border),
                contentDescription = stringResource(id = R.string.search),
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = {
                        searchQuery = ""
                    }
                ) {
                    Icon(
                        painter = painterResource(id = designSystemR.drawable.ic_close),
                        contentDescription = stringResource(
                            id = R.string.search_text_field_clear_icon_description
                        ),
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant ,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.onSurface,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchExplicitlyTriggered()
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onSearchExplicitlyTriggered()
                    true
                } else {
                    false
                }
            },
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchResult(
    scrollBehavior: TopAppBarScrollBehavior,
    onClassItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 12.dp, // 16 - 4 (from its item) = 12
        ),
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        items(
            items = (1..10).toList(),
            key = { it }
        ) {
            ClassCard(
                onItemClick = onClassItemClick,
                modifier = Modifier.padding(
                    vertical = 4.dp,
                )
            )
        }
    }
}

@Composable
private fun JoinRequestClassDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseAlertDialog(
        title = stringResource(id = R.string.join_request_dialog_title),
        dialogText = stringResource(
            R.string.join_request_dialog_text,
            "Pemrograman Mobile B"
        ),
        onConfirm = onDismiss,
        onDismiss = onDismiss,
        confirmText = stringResource(
            id = R.string.join_request_dialog_confirm_button_text
        ),
        modifier = modifier,
    )
}