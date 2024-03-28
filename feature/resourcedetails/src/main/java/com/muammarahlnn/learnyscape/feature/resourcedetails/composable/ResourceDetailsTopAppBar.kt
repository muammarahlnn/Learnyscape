package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.R
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeTopAppbarDefaults
import com.muammarahlnn.learnyscape.core.ui.util.LecturerOnlyComposable

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsTopAppBar, 28/01/2024 15.35
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ResourceDetailsTopAppBar(
    titleRes: Int,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LearnyscapeCenterTopAppBar(
        title = stringResource(id = titleRes),
        colors = LearnyscapeTopAppbarDefaults.defaultTopAppBarColors(),
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_arrow_back
                    ),
                    contentDescription = stringResource(
                        id = R.string.navigation_back_icon_description,
                    ),
                )
            }
        },
        actionsIcon = {
            LecturerOnlyComposable {
                Row {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = stringResource(id = com.muammarahlnn.learnyscape.feature.resourcedetails.R.string.edit_resource)
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = stringResource(id = com.muammarahlnn.learnyscape.feature.resourcedetails.R.string.delete_resource)
                        )
                    }
                }
            }
        },
        modifier = modifier,
    )
}