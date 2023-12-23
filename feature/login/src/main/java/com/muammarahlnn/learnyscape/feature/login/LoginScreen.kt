package com.muammarahlnn.learnyscape.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.muammarahlnn.learnyscape.core.ui.LearnyscapeLogoText
import com.muammarahlnn.learnyscape.core.ui.util.ChangeStatusBarColor
import com.muammarahlnn.learnyscape.core.ui.util.collectInLaunchedEffect
import com.muammarahlnn.learnyscape.core.ui.util.use
import com.muammarahlnn.learnyscape.core.designsystem.R as designSystemR

/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginScreen, 24/09/2023 01.26 by Muammar Ahlan Abimanyu
 */

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    ChangeStatusBarColor(statusBarColor = MaterialTheme.colorScheme.background)

    val (state, event) = use(contract = viewModel)
    val effect = viewModel.effect
    val snackbarHostState = remember { SnackbarHostState() }

    effect.collectInLaunchedEffect {
        when (it) {
            is LoginContract.Effect.ShowSnackbar -> {
                snackbarHostState.showSnackbar(
                    message = it.message,
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
    
    LoginScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onUsernameChange = { username ->
            event(LoginContract.Event.OnUsernameChange(username))
        },
        onPasswordChange = { password ->
            event(LoginContract.Event.OnPasswordChange(password))
        },
        onLoginButtonClick = {
            event(LoginContract.Event.OnLoginButtonClick)
        },
        modifier = modifier,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LoginScreen(
    state: LoginContract.State,
    snackbarHostState: SnackbarHostState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        modifier = modifier.imePadding(),
    ) { padding ->
        LoginContent(
            username = state.username,
            password = state.password,
            isLoginButtonEnabled = state.isLoginButtonEnabled,
            isLoading = state.loading,
            onUsernameChange = onUsernameChange,
            onPasswordChange = onPasswordChange,
            onLoginButtonClick = onLoginButtonClick,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding),
        )
    }
}


@Composable
private fun LoginContent(
    username: String,
    password: String,
    isLoginButtonEnabled: Boolean,
    isLoading: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (
            learnyscapeHeader,
            learningIllustration,
            loginBody,
        ) = createRefs()
        val learningIllustrationImageTopGuideline = createGuidelineFromTop(0.2f)
        val learningIllustrationImageBottomGuideline = createGuidelineFromTop(0.5f)
        val startGuideline = createGuidelineFromStart(16.dp)
        val endGuideline = createGuidelineFromEnd(16.dp)

        LearnyscapeLogoText(
            modifier = Modifier.constrainAs(learnyscapeHeader) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(
                    anchor = learningIllustrationImageTopGuideline,
                    margin = 16.dp
                )
            }
        )

        LearningIllustrationImage(
            modifier = Modifier.constrainAs(learningIllustration) {
                top.linkTo(learningIllustrationImageTopGuideline)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(learningIllustrationImageBottomGuideline)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )

        LoginBody(
            username = username,
            password = password,
            isLoginButtonEnabled = isLoginButtonEnabled,
            isLoading = isLoading,
            onUsernameChange = onUsernameChange,
            onPasswordChange = onPasswordChange,
            onLoginButtonClick = onLoginButtonClick,
            modifier = Modifier.constrainAs(loginBody) {
                top.linkTo(learningIllustrationImageBottomGuideline)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )
    }
}

@Composable
private fun LearningIllustrationImage(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.learning_illustration),
        contentDescription = stringResource(id = R.string.learning_illustration_image_desc),
        modifier = modifier,
    )
}

@Composable
private fun LoginBody(
    username: String,
    password: String,
    isLoginButtonEnabled: Boolean,
    isLoading: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 28.sp,
            ),
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(16.dp))

        UsernameTextField(
            username = username,
            onUsernameChange = onUsernameChange,
        )
        Spacer(modifier = Modifier.height(10.dp))

        PasswordTextField(
            password = password,
            onPasswordChange = onPasswordChange
        )
        Spacer(modifier = Modifier.height(20.dp))

        LoginButton(
            isEnabled = isLoginButtonEnabled,
            isLoading = isLoading,
            onButtonClick = onLoginButtonClick,
        )
    }
}

@Composable
private fun UsernameTextField(
    username: String,
    onUsernameChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = username,
        onValueChange = onUsernameChange,
        placeholder = {
            Text(text = stringResource(id = R.string.username_text_field_placeholder))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = stringResource(id = R.string.user_icon_desc)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.surfaceVariant ,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.onSurface,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
) {
    var showPassword by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = {
            Text(text = stringResource(id = R.string.password_text_field_placeholder))
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_lock),
                contentDescription = stringResource(id = R.string.password_icon_desc)
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
                        contentDescription = stringResource(id = designSystemR.string.visibility_icon_desc)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = designSystemR.drawable.ic_visibility_off),
                        contentDescription = stringResource(id = designSystemR.string.visibility_off_icon_desc)
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
            keyboardType = KeyboardType.Password
        ),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.surfaceVariant ,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
            focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.onSurface,
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}


@Composable
private fun LoginButton(
    isEnabled: Boolean,
    isLoading: Boolean,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // TODO : fix loading state after logged out
    Button(
        onClick = onButtonClick,
        shape = RoundedCornerShape(8.dp),
        enabled = if (isLoading) false else isEnabled,
        modifier = modifier.fillMaxWidth()
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(28.dp)
            )
        } else {
            Text(
                text = stringResource(id = R.string.login),
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}