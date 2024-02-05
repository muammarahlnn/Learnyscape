package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muammarahlnn.learnyscape.core.ui.util.RefreshState
import com.muammarahlnn.learnyscape.feature.resourcedetails.R
import com.muammarahlnn.learnyscape.feature.resourcedetails.ResourceDetailsContract
import kotlinx.coroutines.launch
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File StudentSubmissionBottomSheet, 04/02/2024 14.00
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StudentAssignmentContent(
    state: ResourceDetailsContract.State,
    refreshState: RefreshState,
    event: (ResourceDetailsContract.Event) -> Unit,
    topAppBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val attachments = listOf<String>()

    val density = LocalDensity.current
    var bottomPadding by remember { mutableStateOf(0.dp) }

    Box(modifier = modifier) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetShadowElevation = 8.dp,
            sheetShape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
            ),
            sheetPeekHeight = if (attachments.isEmpty()) 160.dp else 240.dp,
            sheetContent = {
                SheetContent(
                    sheetState = scaffoldState.bottomSheetState,
                    attachments = listOf(),
                    onSeeWorkClick = {
                        coroutineScope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp + bottomPadding,
                        )
                )
            },
            topBar = topAppBar,
        ) { paddingValues ->
            InstructionsContent(
                state = state,
                refreshState = refreshState,
                onAddWorkButtonClick = { event(ResourceDetailsContract.Event.OnAddWorkButtonClick) },
                onStartQuizButtonClick = { event(ResourceDetailsContract.Event.OnStartQuizButtonClick) },
                onAttachmentClick = { event(ResourceDetailsContract.Event.OnAttachmentClick(it)) },
                onRefresh = { event(ResourceDetailsContract.Event.FetchResourceDetails) },
                modifier = Modifier.padding(paddingValues)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.BottomCenter)
        ) {
            SubmissionActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    )
                    .onGloballyPositioned { layoutCoordinates ->
                        bottomPadding = with(density) {
                            layoutCoordinates.size.height.toDp()
                        }
                    }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SheetContent(
    sheetState: SheetState,
    attachments: List<String>,
    onSeeWorkClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        YourWorkText()

        when (sheetState.currentValue) {
            SheetValue.PartiallyExpanded -> PartiallyExpandedSheetContent(
                attachments = attachments,
                onSeeWorkClick = onSeeWorkClick,
            )

            SheetValue.Expanded -> ExpandedSheetContent(
                attachments = attachments
            )

            SheetValue.Hidden -> Unit
        }
    }
}

@Composable
private fun PartiallyExpandedSheetContent(
    attachments: List<String>,
    onSeeWorkClick: () -> Unit,
) {
    if (attachments.isNotEmpty()) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .fillMaxWidth()
                    .clickable { onSeeWorkClick() }
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_attachment),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surfaceVariant
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (attachments.size > 1) {
                        stringResource(id = R.string.plural_attachment, attachments.size)
                    } else stringResource(id = R.string.singular_attachment),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}

@Composable
private fun ExpandedSheetContent(
    attachments: List<String>,
) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.attachments),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 13.sp,
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (attachments.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.add_work_illustration),
                contentDescription = null,
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.no_attachments_uploaded_desc),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            repeat(10) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    SubmissionAttachmentItem(
                        name = "Lorem ipsum dolor sit amet.pdf",
                        modifier = Modifier.weight(0.5f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    SubmissionAttachmentItem(
                        name = "Lorem ipsum dolor sit amet.pdf",
                        modifier = Modifier.weight(0.5f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider(
            color = MaterialTheme.colorScheme.surfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun YourWorkText() {
    Text(
        text = stringResource(id = R.string.your_work),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Medium,
        ),
    )
}

@Composable
private fun SubmissionActionButton(
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {},
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
        ),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = R.string.add_work),
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun SubmissionAttachmentItem(
    name: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onSecondary,
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant,
            ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Icon(
                    painter = painterResource(id = com.muammarahlnn.learnyscape.core.designsystem.R.drawable.ic_document),
                    contentDescription = stringResource(id = com.muammarahlnn.learnyscape.core.designsystem.R.string.attachment_icon_desc),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.Center),
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(bottomStart = 8.dp)
                        )
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .align(Alignment.TopEnd)
                        .padding(
                            vertical = 4.dp,
                            horizontal = 12.dp
                        )
                ) {
                    Icon(
                        painter = painterResource(id = designSystemR.drawable.ic_close),
                        contentDescription = stringResource(id = R.string.remove_attachment),
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                LinearProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))


        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
    }
}