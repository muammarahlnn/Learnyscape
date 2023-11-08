package com.muammarahlnn.learnyscape.feature.member

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file MemberScreen, 03/08/2023 15.46 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun MemberRoute(
    modifier: Modifier = Modifier,
) {
    MemberScreen(
        modifier = modifier,
    )
}

@Composable
private fun MemberScreen(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier,
    ) {
        item {
            TeachersCard()
        }
        
        item { 
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            StudentsCard()
        }
    }
}


@Composable
private fun TeachersCard(modifier: Modifier = Modifier) {
    BaseMemberCard(
        title = stringResource(id = R.string.teachers),
        modifier = modifier,
    ) {
        repeat(2) {
            MemberRow(
                avaId = R.drawable.ava_luffy,
                name = "Andi Muh. Amil Siddik S.Si., M.Si"
            )
        }
    }
}

@Composable
private fun StudentsCard(modifier: Modifier = Modifier) {
    BaseMemberCard(
        title = stringResource(id = R.string.students),
        modifier = modifier,
    ) {
        repeat(15) {
            MemberRow(
                avaId = null,
                name = "Lorem ipsum dolor sit amet"
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

            Divider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.background
            )

            Column(Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Composable
private fun MemberRow(
    @DrawableRes avaId: Int?,
    name: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 12.dp)
    ) {
        if (avaId != null) {
            Image(
                painter = painterResource(
                    id = avaId,
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )
        } else {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_person_border),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

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