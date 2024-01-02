package com.muammarahlnn.learnyscape.feature.resourcecreate.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseAlertDialog
import com.muammarahlnn.learnyscape.feature.resourcecreate.R

/**
 * @Author Muammar Ahlan Abimanyu
 * @File SetDurationDialog, 28/12/2023 02.55
 */
@Composable
internal fun SetDurationDialog(
    duration: Int,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    var currentDuration by rememberSaveable { mutableIntStateOf(duration) }
    BaseAlertDialog(
        title = stringResource(id = R.string.duration),
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.width(60.dp)
                ) {
                    TextField(
                        value = if (currentDuration != 0) currentDuration.toString() else "",
                        onValueChange = {
                            if (it.length <= 3) {
                                currentDuration =
                                    if (it.isNotEmpty()) it.toInt()
                                    else 0
                            }
                        },
                        placeholder = {
                            Text(
                                text = currentDuration.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword, // to show only number without comma or dot
                            imeAction = ImeAction.Done,
                        ),
                        maxLines = 1,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodySmall,
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.outline,
                            focusedContainerColor = MaterialTheme.colorScheme.outline,
                            disabledContainerColor = MaterialTheme.colorScheme.outline,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            cursorColor = MaterialTheme.colorScheme.onSurface,
                        ),
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = stringResource(id = R.string.minutes),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
        onConfirm = {
            onConfirm(currentDuration)
        },
        onDismiss = onDismiss,
        confirmText = stringResource(id = R.string.due_date_picker_dialog_confirm_text),
        dismissText = stringResource(id = R.string.due_date_picker_dialog_cancel_text)
    )
}