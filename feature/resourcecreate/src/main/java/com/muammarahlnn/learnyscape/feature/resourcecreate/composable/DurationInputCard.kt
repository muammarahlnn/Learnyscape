package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.muammarahlnn.learnyscape.core.ui.util.noRippleClickable
import com.muammarahlnn.learnyscape.feature.resourcecreate.R
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @Author Muammar Ahlan Abimanyu
 * @File DurationInputCard, 28/12/2023 02.07
 */
@Composable
internal fun DurationInputCard(
    duration: Int,
    onDurationClick: () -> Unit,
) {
    InputCard(
        iconRes = designSystemR.drawable.ic_timer,
        iconDescriptionRes = R.string.duration,
        modifier = Modifier.noRippleClickable {
            onDurationClick()
        }
    ) {
        Text(
            text = if (duration == 0) stringResource(id = R.string.duration) else duration.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}