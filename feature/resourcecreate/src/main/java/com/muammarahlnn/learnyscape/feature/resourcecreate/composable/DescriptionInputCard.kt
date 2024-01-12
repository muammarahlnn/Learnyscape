package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.TransparentTextField
import com.muammarahlnn.learnyscape.feature.resourcecreate.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DescriptionInputCard, 25/12/2023 04.25
 */
@Composable
internal fun DescriptionInputCard(
    resourceType: ClassResourceType,
    description: String,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    InputCard(
        iconRes = R.drawable.ic_subject,
        iconDescriptionRes = R.string.description,
        modifier = modifier,
    ) {
        TransparentTextField(
            value = description,
            placeholderText = stringResource(
                id = when (resourceType) {
                    ClassResourceType.ANNOUNCEMENT -> R.string.share_with_your_class
                    else -> R.string.description
                }
            ),
            onValueChange = onDescriptionChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}