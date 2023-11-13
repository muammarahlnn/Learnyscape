package com.muammarahlnn.learnyscape.feature.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.ui.ClassCard
import com.muammarahlnn.learnyscape.core.ui.SearchTextField


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file SearchScreen, 20/07/2023 22.06 by Muammar Ahlan Abimanyu
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchRoute(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    var showJoinRequestDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    SearchScreen(
        scrollBehavior = scrollBehavior,
        searchQuery = searchQuery,
        showJoinRequestDialog = showJoinRequestDialog,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onClassItemClick = {
            showJoinRequestDialog = true
        },
        onDismissJoinRequestDialog = {
            showJoinRequestDialog = false
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    showJoinRequestDialog: Boolean,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onClassItemClick: () -> Unit,
    onDismissJoinRequestDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (showJoinRequestDialog) {
        JoinRequestClassDialog(
            onDismiss = onDismissJoinRequestDialog,
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchTextField(
            searchQuery = searchQuery,
            placeholderText = stringResource(
                id = R.string.search_available_class_placeholder
            ),
            onSearchQueryChanged = onSearchQueryChanged,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 12.dp,
            )
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .weight(1f)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            items(
                items = (1..10).toList(),
                key = { it }
            ) {
                ClassCard(
                    onItemClick = onClassItemClick
                )
            }
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