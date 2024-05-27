package com.muammarahlnn.learnyscape.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import com.muammarahlnn.learnyscape.core.testing.data.enrolledClassesInfoModelTestData
import com.muammarahlnn.learnyscape.core.testing.data.refreshStateTestData
import org.junit.Rule
import org.junit.Test

/**
 * @Author Muammar Ahlan Abimanyu
 * @File HomeScreenTest, 27/05/2024 16.46
 */
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun homeContentLoading_whenScreenIsLoading_exists() {
        composeTestRule.setContent {
            HomeScreen(
                state = HomeContract.State(
                    uiState = HomeContract.UiState.Loading
                ),
                refreshState = refreshStateTestData,
                event = {_ -> },
                navigate = {_ -> },
            )
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(R.string.home_loading_desc)
            )
            .assertExists()
    }

    @Test
    fun errorScreen_whenError_exists() {
        val testErrorMessage = "Error message"
        composeTestRule.setContent {
            HomeScreen(
                state = HomeContract.State(
                    uiState = HomeContract.UiState.Error(testErrorMessage)
                ),
                refreshState = refreshStateTestData,
                event = {_ -> },
                navigate = {_ -> },
            )
        }

        composeTestRule
            .onNodeWithText(testErrorMessage)
            .assertExists()
    }

    @Test
    fun noInternetScreen_whenNoInternet_exists() {
        val testNoInternetMessage = "No internet"
        composeTestRule.setContent {
            HomeScreen(
                state = HomeContract.State(
                    uiState = HomeContract.UiState.NoInternet(testNoInternetMessage)
                ),
                refreshState = refreshStateTestData,
                event = {_ -> },
                navigate = {_ -> },
            )
        }

        composeTestRule
            .onNodeWithText(testNoInternetMessage)
            .assertExists()
    }

    @Test
    fun emptyScreen_whenSuccessEmpty_exists() {
        composeTestRule.setContent {
            HomeScreen(
                state = HomeContract.State(
                    uiState = HomeContract.UiState.SuccessEmpty
                ),
                refreshState = refreshStateTestData,
                event = {_ -> },
                navigate = {_ -> },
            )
        }

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.resources.getString(R.string.empty_class_illustration_desc)
            )
            .assertExists()
    }

    @Test
    fun enrolledClasses_whenSuccess_exists() {
        composeTestRule.setContent {
            HomeScreen(
                state = HomeContract.State(
                    uiState = HomeContract.UiState.Success,
                    classes = enrolledClassesInfoModelTestData,
                    searchedClasses = enrolledClassesInfoModelTestData,
                ),
                refreshState = refreshStateTestData,
                event = {_ -> },
                navigate = {_ -> },
            )
        }

        composeTestRule
            .onNodeWithText(enrolledClassesInfoModelTestData[0].className)
            .assertExists()
            .assertHasClickAction()

        composeTestRule
            .onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(enrolledClassesInfoModelTestData[1].className)
            )

        composeTestRule
            .onNodeWithText(enrolledClassesInfoModelTestData[1].className)
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun searchTextField_whenSuccess_exists() {
        composeTestRule.setContent {
            HomeScreen(
                state = HomeContract.State(
                    uiState = HomeContract.UiState.Success,
                ),
                refreshState = refreshStateTestData,
                event = {_ -> },
                navigate = {_ -> },
            )
        }

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.resources.getString(R.string.search_placeholder_text)
            )
            .assertExists()
    }
}