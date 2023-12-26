package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.TransparentTextField
import com.muammarahlnn.learnyscape.feature.resourcecreate.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File TitleInputCard, 26/12/2023 07.26
 */
@Composable
internal fun TitleInputCard(
    resourceType: ClassResourceType,
    title: String,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    InputCard(
        iconRes = R.drawable.ic_title,
        iconDescriptionRes = R.string.resource_title,
        modifier = modifier,
    ) {
        TransparentTextField(
            value = title,
            placeholderText = stringResource(
                id = R.string.resource_title_placeholder,
                stringResource(id = resourceType.nameRes)
            ),
            onValueChange = onTitleChange
        )
    }
}