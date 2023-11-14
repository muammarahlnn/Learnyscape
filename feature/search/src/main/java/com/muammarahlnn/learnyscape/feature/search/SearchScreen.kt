package com.muammarahlnn.learnyscape.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(1f)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            items(
                items = (1..10).toList(),
                key = { it },
            ) {
                SearchedClassCard(
                    onClassClick = onClassItemClick,
                    index = it,
                )
            }
        }
    }
}

@Composable
private fun SearchedClassCard(
    index: Int,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseCard(
        modifier = modifier.clickable {
            onClassClick()
        }
    ) {
        Column{
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_school),
                    contentDescription = stringResource(id = R.string.available_class),
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(64.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    )
            ) {
                Text(
                    text = if (index % 2 == 0) "Pemrograman Mobile A" else "Pemrograman Basis Data dan Normalisasi B",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "Dr. Hendra, S.Si., M.Kom.",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 10.sp,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tuesday, 15:00 - 16:30",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
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