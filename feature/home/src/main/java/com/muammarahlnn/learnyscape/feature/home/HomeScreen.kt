package com.muammarahlnn.learnyscape.feature.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muammarahlnn.learnyscape.core.model.data.ClassInfoModel
import com.muammarahlnn.learnyscape.core.ui.ClassCard
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.SearchTextField
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file HomeScreen, 20/07/2023 19.56 by Muammar Ahlan Abimanyu
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeRoute(
    scrollBehavior: TopAppBarScrollBehavior,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        scrollBehavior = scrollBehavior,
        searchQuery = searchQuery,
        onRefresh = viewModel::fetchClasses,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onClassClick = onClassClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    scrollBehavior: TopAppBarScrollBehavior,
    searchQuery: String,
    onRefresh: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        HomeUiState.Loading -> {
            HomeContentLoading()
        }

        is HomeUiState.Success -> {
            HomeContent(
                searchQuery = searchQuery,
                classes = uiState.classes,
                onSearchQueryChanged = onSearchQueryChanged,
                onClassClick = onClassClick,
                modifier = modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            )
        }

        HomeUiState.SuccessEmptyClasses -> {
            EmptyClassesContent(
                modifier = modifier,
            )
        }

        is HomeUiState.Error -> {
            ErrorScreen(
                text = uiState.message,
                onRefresh = onRefresh,
                modifier = modifier.fillMaxSize()
            )
        }

        is HomeUiState.NoInternet -> {
            NoInternetContent(
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun HomeContent(
    searchQuery: String,
    classes: List<ClassInfoModel>,
    onSearchQueryChanged: (String) -> Unit,
    onClassClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SearchTextField(
            searchQuery = searchQuery,
            placeholderText = stringResource(id = R.string.search_placeholder_text),
            onSearchQueryChanged = onSearchQueryChanged,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                )
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = classes,
                key = {
                    it.id
                }
            ) { classInfo ->
                ClassCard(
                    className = classInfo.className,
                    lecturerName = classInfo.lecturerNames.first(),
                    onItemClick = onClassClick,
                )
            }
        }
    }
}

@Composable
private fun HomeContentLoading(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(32.dp))

        repeat(5) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun EmptyClassesContent(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_data_illustration),
            contentDescription = null,
            modifier = Modifier.size(128.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.empty_class_illustration_desc),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }

}

@Composable
private fun NoInternetContent(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_wifi_off),
            contentDescription = stringResource(id = R.string.wifi_off_icon_desc),
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(128.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.wifi_off_icon_desc),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}