package com.muammarahlnn.learnyscape.feature.resourcedetails.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.muammarahlnn.learnyscape.core.ui.ClassResourceType
import com.muammarahlnn.learnyscape.core.ui.PostCard

/**
 * @Author Muammar Ahlan Abimanyu
 * @File ResourceDetailsCard, 28/01/2024 15.37
 */
@Composable
internal fun ResourceDetailsCard(
    resourceType: ClassResourceType,
    name: String,
    date: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    PostCard(
        classResourceType = resourceType,
        title = name,
        timePosted = date,
        caption = description,
        isCaptionOverflowed = false,
        modifier = modifier,
    )
}