package com.muammarahlnn.learnyscape.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.muammarahlnn.learnyscape.core.ui.LearnyscapeText


/**
 * @author Muammar Ahlan Abimanyu (muammarahlnn)
 * @file LoginScreen, 24/09/2023 01.26 by Muammar Ahlan Abimanyu
 */

@Composable
internal fun LoginRoute(
    onLoginButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LoginScreen(
        onLoginButtonClick = onLoginButtonClick,
        modifier = modifier,
    )
}

@Composable
private fun LoginScreen(
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

        LearnyscapeHeader(
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
private fun LearnyscapeHeader(
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_learnyscape_circle),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        LearnyscapeText()
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
        UsernameTextField()
        Spacer(modifier = Modifier.height(10.dp))
        PasswordTextField()
        Spacer(modifier = Modifier.height(20.dp))
        LoginButton(
            onButtonClick = onLoginButtonClick,
        )
    }
}

@Composable
private fun UsernameTextField() {
    var usernameValue by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = usernameValue,
        onValueChange = { value ->
            usernameValue = value
        },
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
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant ,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
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
private fun PasswordTextField() {
    var passwordValue by rememberSaveable { mutableStateOf("") }
    var showPassword by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = passwordValue,
        onValueChange = { value ->
            passwordValue = value
        },
        placeholder = {
            Text(text = stringResource(id = R.string.username_text_field_placeholder))
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
                        painter = painterResource(id = R.drawable.ic_visibility),
                        contentDescription = stringResource(id = R.string.visibility_icon_desc)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_visibility_off),
                        contentDescription = stringResource(id = R.string.visibility_off_icon_desc)
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
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant ,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
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
    onButtonClick: () -> Unit,
) {
    Button(
        onClick = onButtonClick,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.login),
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}