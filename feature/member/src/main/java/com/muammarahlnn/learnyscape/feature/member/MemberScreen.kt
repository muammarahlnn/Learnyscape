package com.muammarahlnn.learnyscape.feature.member

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppbarDefaults
import com.muammarahlnn.learnyscape.core.ui.ErrorScreen
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImage
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.core.ui.PullRefreshScreen
import com.muammarahlnn.learnyscape.core.ui.util.CollectEffect
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.core.ui.util.shimmerEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import kotlinx.coroutines.launch
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file MemberScreen, 03/08/2023 15.46 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun MemberRoute(
    classId: String,
    controller: MemberController,
    modifier: Modifier = Modifier,
    viewModel: MemberViewModel = hiltViewModel(),
) {
    CollectEffect(controller.navigation) { navigation ->
        when (navigation) {
            MemberNavigation.NavigateBack ->
                controller.navigateBack()
        }
    }

    val (state, event) = use(contract = viewModel)
    LaunchedEffect(Unit) {
        launch {
            event(MemberContract.Event.SetClassId(classId))
        }.join()

        launch {
            event(MemberContract.Event.FetchClassMembers)
        }
    }

    MemberScreen(
        state = state,
        refreshState = use(viewModel) { event(MemberContract.Event.FetchClassMembers) },
        event = { event(it) },
        navigate = controller::navigate,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun MemberScreen(
    state: MemberContract.State,
    refreshState: RefreshState,
    event: (MemberContract.Event) -> Unit,
    navigate: (MemberNavigation) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            LearnyscapeCenterTopAppBar(
                title = stringResource(id = R.string.member),
                navigationIcon = {
                    IconButton(
                        onClick = { navigate(MemberNavigation.NavigateBack) }
                    ) {
                        Icon(
                            painter = painterResource(id = designSystemR.drawable.ic_arrow_back),
                            contentDescription = stringResource(
                                id = designSystemR.string.navigation_back_icon_description
                            )
                        )
                    }
                },
                colors = LearnyscapeTopAppbarDefaults.classTopAppBarColors(),
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        PullRefreshScreen(
            pullRefreshState = refreshState.pullRefreshState,
            refreshing = refreshState.refreshing,
            modifier = Modifier.padding(paddingValues)
        ) {
            val contentModifier = Modifier.fillMaxSize()
            when (state.uiState) {
                MemberContract.UiState.Loading -> MemberScreenLoadingContent(
                    modifier = contentModifier
                )

                is MemberContract.UiState.Success -> LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = contentModifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                ) {
                    item {
                        TeachersCard(state.lecturers)
                    }

                    if (state.students.isNotEmpty()) {
                        item {
                            StudentsCard(state.students)
                        }
                    }
                }

                is MemberContract.UiState.Error -> ErrorScreen(
                    text = state.uiState.message,
                    onRefresh = { event(MemberContract.Event.FetchClassMembers) },
                    modifier = contentModifier
                )
            }
        }
    }
}

@Composable
private fun MemberScreenLoadingContent(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .clip(RoundedCornerShape(8.dp))
                .shimmerEffect()
        )
    }
}


@Composable
private fun TeachersCard(
    teachers: List<MemberContract.ClassMemberState>,
    modifier: Modifier = Modifier
) {
    BaseMemberCard(
        title = stringResource(id = R.string.teachers),
        modifier = modifier,
    ) {
        teachers.forEach { teacher ->
            MemberRow(
                photoProfileImageUiState = teacher.profilePicUiState,
                name = teacher.name
            )
        }
    }
}

@Composable
private fun StudentsCard(
    students: List<MemberContract.ClassMemberState>,
    modifier: Modifier = Modifier
) {
    BaseMemberCard(
        title = stringResource(id = R.string.students),
        modifier = modifier,
    ) {
        students.forEach { student ->
            MemberRow(
                photoProfileImageUiState = student.profilePicUiState,
                name = student.name
            )
        }
    }
}

@Composable
private fun BaseMemberCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    BaseCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(
                    top = 16.dp,
                    start = 16.dp,
                    end= 16.dp,
                    bottom = 8.dp,
                )
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.outline,
            )

            Column(Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Composable
private fun MemberRow(
    photoProfileImageUiState: PhotoProfileImageUiState,
    name: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        PhotoProfileImage(
            uiState = photoProfileImageUiState,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = name,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}