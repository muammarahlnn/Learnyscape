package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.designsystem.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File AddWorkButton, 28/01/2024 15.35
 */
@Composable
internal fun AddWorkButton(
    onButtonClick: () -> Unit,
    onButtonGloballyPositioned: (LayoutCoordinates) -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseActionButton(
        onButtonClick = onButtonClick,
        onButtonGloballyPositioned = onButtonGloballyPositioned,
        modifier = modifier,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = stringResource(
                id = R.string.add_icon_description,
            )
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = stringResource(id = com.muammarahlnn.learnyscape.feature.resourcedetails.R.string.add_work))
    }
}