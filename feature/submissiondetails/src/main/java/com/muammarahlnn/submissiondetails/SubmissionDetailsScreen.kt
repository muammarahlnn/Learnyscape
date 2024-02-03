package com.muammarahlnn.submissiondetails

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.model.data.SubmissionType
import com.muammarahlnn.learnyscape.core.ui.AttachmentItem
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImage
import com.muammarahlnn.learnyscape.core.ui.PhotoProfileImageUiState
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SubmissionDetailsScreen, 02/02/2024 18.27
 */
@Composable
internal fun SubmissionDetailsRoute(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SubmissionDetailsViewModel = hiltViewModel(),
) {
    SubmissionDetailsScreen(
        submissionType = viewModel.submissionType,
        navigateBack = navigateBack,
        modifier = modifier,
    )
}

@Composable
private fun SubmissionDetailsScreen(
    submissionType: SubmissionType,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 2.dp,
                    shape = CircleShape,
                )
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .size(32.dp)
                .clickable { navigateBack() }
        ) {
            Icon(
                painter = painterResource(id = designSystemR.drawable.ic_arrow_back),
                contentDescription = stringResource(
                    id = designSystemR.string.navigation_back_icon_description
                ),
                modifier = Modifier.padding(6.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (submissionType) {
            SubmissionType.ASSIGNMENT -> StudentSubmissionCard(
                modifier = Modifier.fillMaxWidth()
            )

            SubmissionType.QUIZ -> StudentQuizAnswersContent()
        }
    }
}

@Composable
private fun StudentSubmissionCard(
    modifier: Modifier = Modifier,
) {
    BaseCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            StudentSubmissionDetailsRow(
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            repeat(2) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    AttachmentItem(
                        name = "Lorem ipsum dolor sit amet.pdf",
                        modifier = Modifier.weight(0.5f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    AttachmentItem(
                        name = "Lorem ipsum dolor sit amet.pdf",
                        modifier = Modifier.weight(0.5f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun StudentQuizAnswersContent(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        BaseCard(modifier = modifier) {
            StudentSubmissionDetailsRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        MultipleChoiceAnswers()
    }
}

@Composable
private fun StudentSubmissionDetailsRow(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        PhotoProfileImage(
            uiState = PhotoProfileImageUiState.Success(null),
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = "Lorem ipsum dolor sit amet",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Turned in at 21:12",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
    }
}

@Composable
private fun MultipleChoiceAnswers() {
    Column {
        repeat(5) {
            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .shadow(
                            elevation = 2.dp,
                            shape = CircleShape,
                        )
                        .background(MaterialTheme.colorScheme.background)
                        .clip(CircleShape)
                        .size(30.dp)
                        .padding(5.dp)
                ) {
                    Text(
                        text = it.toString(),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                BaseCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "A.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.onBackground,
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = "Lorem ipsum dolor sit amet, lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, lorem ipsum dolor sit amet.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}