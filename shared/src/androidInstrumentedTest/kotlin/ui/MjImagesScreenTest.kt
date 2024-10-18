package ui

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import domain.model.State
import midjourneyimagescomposemultiplatform.shared.generated.resources.Res
import midjourneyimagescomposemultiplatform.shared.generated.resources.snack_message
import org.jetbrains.compose.resources.stringResource
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
                context = LocalContext.current,
                remoteDataSource = EmptyMjImagesDataSource(),
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
    fun testOfflineScreenUi() {
        var viewModel: MjImagesViewModel? = null

        composeTestRule.setContent {
            MjImagesApp(initAppAndMockViewModel(
                context = LocalContext.current,
                remoteDataSource = ErrorMjImagesDataSource(),
                localDataSource = OfflineMjImagesLocalDataSource(),
            ).also { viewModel = it })
        }

        composeTestRule
            .waitUntil(3000) {
                viewModel?.state?.value == State.CONTENT
            }

        composeTestRule
            .onNodeWithText("offline", substring = true, ignoreCase = true)
            .assertIsDisplayed()
    }

    @Test
    fun testMjImagesScreenUi() {
        var viewModel: MjImagesViewModel? = null

        composeTestRule.setContent {
            MjImagesApp(initAppAndMockViewModel(
                context = LocalContext.current,
                remoteDataSource = SuccessMjImagesDataSource(),
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
        var snackMessage = ""

        composeTestRule.setContent {
            MjImagesApp(initAppAndMockViewModel(
                context = LocalContext.current,
                remoteDataSource = SuccessMjImagesDataSource(),
            ).also { viewModel = it })
            snackMessage = stringResource(Res.string.snack_message)
        }

        composeTestRule
            .waitUntil(3000) {
                viewModel?.state?.value == State.CONTENT
            }

        composeTestRule
            .onNodeWithText(snackMessage)
            .assertIsDisplayed()
    }
}
