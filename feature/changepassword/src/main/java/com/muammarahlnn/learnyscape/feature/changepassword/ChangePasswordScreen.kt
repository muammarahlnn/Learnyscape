package com.muammarahlnn.learnyscape.feature.changepassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseCard
import com.muammarahlnn.learnyscape.core.designsystem.component.BaseEditableBasicTextField
import com.muammarahlnn.learnyscape.core.designsystem.component.LearnyscapeCenterTopAppBar
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file ChangePasswordScreen, 14/11/2023 11.20 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun ChangePasswordRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChangePasswordViewModel = hiltViewModel(),
) {
    ChangePasswordScreen(
        oldPassword = viewModel.oldPassword,
        newPassword = viewModel.newPassword,
        confirmNewPassword = viewModel.confirmNewPassword,
        onBackClick = onBackClick,
        onOldPasswordChange = viewModel::setOldPasswordValue,
        onNewPasswordChange = viewModel::setNewPasswordValue,
        onConfirmNewPasswordChange = viewModel::setConfirmNewPasswordValue,
        onSaveButtonClick = {

        },
        modifier = modifier,
    )
}

@Composable
private fun ChangePasswordScreen(
    oldPassword: String,
    newPassword: String,
    confirmNewPassword: String,
    onBackClick: () -> Unit,
    onOldPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onConfirmNewPasswordChange: (String) -> Unit,
    onSaveButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            ChangePasswordTopAppBar(
                onNavigationClick = onBackClick
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            BaseCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    ChangePasswordTitleText()
                    Spacer(modifier = Modifier.height(16.dp))

                    ChangePasswordTextField(
                        title = stringResource(id = R.string.old_password),
                        value = oldPassword,
                        imeAction = ImeAction.Next,
                        onValueChange = onOldPasswordChange
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    ChangePasswordTextField(
                        title = stringResource(id = R.string.new_password),
                        value = newPassword,
                        imeAction = ImeAction.Next,
                        onValueChange = onNewPasswordChange
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    ChangePasswordTextField(
                        title = stringResource(id = R.string.confirm_new_password),
                        value = confirmNewPassword,
                        imeAction = ImeAction.Done,
                        onValueChange = onConfirmNewPasswordChange
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onSaveButtonClick,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.save),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChangePasswordTitleText() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = designSystemR.drawable.ic_vpn_key),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.new_password),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun ChangePasswordTextField(
    title: String,
    value: String,
    imeAction: ImeAction,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }

    BaseEditableBasicTextField(
        title = title,
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    showPassword = !showPassword
                }
            ) {
                if (!showPassword) {
                    Icon(
                        painter = painterResource(id = designSystemR.drawable.ic_visibility),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surfaceVariant,
                    )
                } else {
                    Icon(
                        painter = painterResource(id = designSystemR.drawable.ic_visibility_off),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
        },
        visualTransformation = if (!showPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction,
        ),
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChangePasswordTopAppBar(
    onNavigationClick: () -> Unit,
) {
    LearnyscapeCenterTopAppBar(
        title = stringResource(id = R.string.change_password),
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    painter = painterResource(id = designSystemR.drawable.ic_arrow_back),
                    contentDescription = stringResource(id = designSystemR.string.navigation_back_icon_description),
                )
            }
        },
    )
}