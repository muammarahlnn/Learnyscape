package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.ui.TransparentTextField
import com.muammarahlnn.learnyscape.feature.resourcecreate.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DescriptionInputCard, 25/12/2023 04.25
 */
@Composable
internal fun DescriptionInputCard(
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
            placeholderText = stringResource(id = R.string.description_input_placeholder),
            onValueChange = onDescriptionChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}