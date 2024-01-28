package com.muammarahlnn.learnyscape.feature.aclass

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.feature.aclass.composable.ClassErrorContent
import com.muammarahlnn.learnyscape.feature.aclass.composable.ClassLoadingContent
import com.muammarahlnn.learnyscape.feature.aclass.composable.ClassSuccessContent


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ClassScreen, 04/08/2023 00.07 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ClassRoute(
    classId: String,
    navigateBack: () -> Unit,
    navigateToJoinRequests: (String) -> Unit,
    navigateToResourceCreate: (String, Int) -> Unit,
    navigateToResourceDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClassViewModel = hiltViewModel(),
) {
    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        sequence {
            yield(ClassContract.Event.SetClassId(classId))
            yield(ClassContract.Event.FetchClassFeeds)
            yield(ClassContract.Event.FetchProfilePic)
        }.forEach { event ->
            event(event)
        }
    }

    val context = LocalContext.current
    viewModel.effect.collectInLaunchedEffect {
        when (it) {
            is ClassContract.Effect.ShowToast ->
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()

            ClassContract.Effect.NavigateBack ->
                navigateBack()

            is ClassContract.Effect.NavigateToJoinRequests ->
                navigateToJoinRequests(it.classId)

            is ClassContract.Effect.NavigateToResourceCreate ->
                navigateToResourceCreate(it.classId, it.resourceTypeOrdinal)

            is ClassContract.Effect.NavigateToResourceDetails ->
                navigateToResourceDetails(it.resourceTypeOrdinal)
        }
    }

    val refreshState = use(refreshProvider = viewModel) {
        event(ClassContract.Event.FetchClassFeeds)
        event(ClassContract.Event.FetchProfilePic)
    }

    ClassScreen(
        state = state,
        refreshState = refreshState,
        event = { event(it) },
        modifier = modifier,
    )
}

@Composable
private fun ClassScreen(
    state: ClassContract.State,
    refreshState: RefreshState,
    event: (ClassContract.Event) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state.uiState) {
        ClassContract.UiState.Loading -> ClassLoadingContent(
            onBackClick = { event(ClassContract.Event.OnNavigateBack) },
            onJoinRequestsClick = { event(ClassContract.Event.OnNavigateToJoinRequests) },
        )

        is ClassContract.UiState.Success -> ClassSuccessContent(
            refreshState = refreshState,
            profilePicUiState = state.profilePicUiState,
            classFeeds = state.uiState.classFeeds,
            announcementAuthorProfilePicUiStateMap = state.announcementAuthorProfilePicUiStateMap,
            onBackClick = { event(ClassContract.Event.OnNavigateBack) },
            onJoinRequestsClick = { event(ClassContract.Event.OnNavigateToJoinRequests) },
            onCreateNewAnnouncementClick = { event(ClassContract.Event.OnNavigateToResourceCreate) },
            modifier = modifier,
        )

        is ClassContract.UiState.Error -> ClassErrorContent(
            errorMessage = state.uiState.message,
            onBackClick = { event(ClassContract.Event.OnNavigateBack) },
            onJoinRequestsClick = { event(ClassContract.Event.OnNavigateToJoinRequests) },
            onRefresh = {
                event(ClassContract.Event.FetchClassFeeds)
                event(ClassContract.Event.FetchProfilePic)
            },
        )
    }
}