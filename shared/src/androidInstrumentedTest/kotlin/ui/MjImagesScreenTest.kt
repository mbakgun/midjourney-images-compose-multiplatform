package ui

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import domain.model.State
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin

class MjImagesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testEmptyScreenUi() {
        var viewModel: MjImagesViewModel? = null

        composeTestRule.setContent {
            MjImagesApp(initAppAndMockViewModel(
                LocalContext.current,
                EmptyMjImagesDataSource()
            ).also { viewModel = it })
        }

        composeTestRule
            .waitUntil(3000) {
                viewModel?.state?.value == State.EMPTY
            }

        composeTestRule
            .onNodeWithText("No images")
            .assertIsDisplayed()
    }

    @Test
    fun testErrorScreenUi() {
        var viewModel: MjImagesViewModel? = null

        composeTestRule.setContent {
            MjImagesApp(initAppAndMockViewModel(
                LocalContext.current,
                ErrorMjImagesDataSource()
            ).also { viewModel = it })
        }

        composeTestRule
            .waitUntil(3000) {
                viewModel?.state?.value == State.ERROR
            }

        composeTestRule
            .onNodeWithText("Error")
            .assertIsDisplayed()
    }

    @Test
    fun testMjImagesScreenUi() {
        var viewModel: MjImagesViewModel? = null

        composeTestRule.setContent {
            MjImagesApp(initAppAndMockViewModel(
                LocalContext.current,
                SuccessMjImagesDataSource()
            ).also { viewModel = it })
        }

        composeTestRule
            .waitUntil(3000) {
                viewModel?.state?.value == State.CONTENT
            }

        composeTestRule
            .onNodeWithTag("imagesGrid")
            .assertIsDisplayed()
    }

    @Test
    fun testMjImagesScreen() {
        var viewModel: MjImagesViewModel? = null

        composeTestRule.setContent {
            MjImagesApp(initAppAndMockViewModel(
                LocalContext.current,
            ).also { viewModel = it })
        }

        composeTestRule
            .waitUntil(5000) {
                viewModel?.state?.value == State.CONTENT
            }

        composeTestRule
            .onNodeWithTag("imagesGrid")
            .assertIsDisplayed()
    }

    @Test
    fun testMjImagesSnackMessage() {
        var viewModel: MjImagesViewModel? = null

        composeTestRule.setContent {
            MjImagesApp(initAppAndMockViewModel(
                LocalContext.current,
                SuccessMjImagesDataSource()
            ).also { viewModel = it })
        }

        composeTestRule
            .waitUntil(3000) {
                viewModel?.state?.value == State.CONTENT
            }

        composeTestRule
            .onNodeWithText(SNACK_MESSAGE)
            .assertIsDisplayed()
    }
}
