package com.muammarahlnn.learnyscape.feature.aclass

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
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
    controller: ClassController,
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

    CollectEffect(controller.navigation) { navigation ->
        when (navigation) {
            ClassNavigation.NavigateBack ->
                controller.navigateBack()

            is ClassNavigation.NavigateToJoinRequests ->
                controller.navigateToJoinRequests(navigation.classId)

            is ClassNavigation.NavigateToResourceCreate ->
                controller.navigateToResourceCreate(
                    navigation.classId,
                    navigation.resourceTypeOrdinal,
                )

            is ClassNavigation.NavigateToResourceDetails ->
                controller.navigateToResourceDetails(
                    navigation.resourceId,
                    navigation.resourceTypeOrdinal,
                )
        }
    }

    val context = LocalContext.current
    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is ClassContract.Effect.ShowToast ->
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
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
        navigate = controller::navigate,
        modifier = modifier,
    )
}

@Composable
private fun ClassScreen(
    state: ClassContract.State,
    refreshState: RefreshState,
    event: (ClassContract.Event) -> Unit,
    navigate: (ClassNavigation) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state.uiState) {
        ClassContract.UiState.Loading -> ClassLoadingContent(
            onBackClick = { navigate(ClassNavigation.NavigateBack) },
            onJoinRequestsClick = {
                navigate(ClassNavigation.NavigateToJoinRequests(state.classId))
            },
        )

        is ClassContract.UiState.Success -> ClassSuccessContent(
            refreshState = refreshState,
            profilePicUiState = state.profilePicUiState,
            classFeeds = state.uiState.classFeeds,
            announcementAuthorProfilePicUiStateMap = state.announcementAuthorProfilePicUiStateMap,
            onBackClick = { navigate(ClassNavigation.NavigateBack) },
            onJoinRequestsClick = { navigate(ClassNavigation.NavigateToJoinRequests(state.classId)) },
            onCreateNewAnnouncementClick = {
                navigate(ClassNavigation.NavigateToResourceCreate(
                    classId = state.classId,
                    resourceTypeOrdinal = state.announcementOrdinal,
                ))
            },
            onFeedClick = { feedId, resourceTypeOrdinal ->
                navigate(ClassNavigation.NavigateToResourceDetails(
                    resourceId = feedId,
                    resourceTypeOrdinal = resourceTypeOrdinal,
                ))
            },
            modifier = modifier,
        )

        is ClassContract.UiState.Error -> ClassErrorContent(
            errorMessage = state.uiState.message,
            onBackClick = { navigate(ClassNavigation.NavigateBack) },
            onJoinRequestsClick = { navigate(ClassNavigation.NavigateToJoinRequests(state.classId)) },
            onRefresh = {
                event(ClassContract.Event.FetchClassFeeds)
                event(ClassContract.Event.FetchProfilePic)
            },
        )
    }
}