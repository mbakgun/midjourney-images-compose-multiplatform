package ui

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import domain.model.State
import org.junit.Rule
import org.junit.Test

class MjImagesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEmptyScreen() {
        val message = "No images"

        composeTestRule.setContent {
            EmptyScreen(onRefresh = { })
        }

        composeTestRule
            .onNodeWithText(message)
            .assertIsDisplayed()
    }

    @Test
    fun testErrorScreen() {
        val message = "Error"

        composeTestRule.setContent {
            ErrorScreen(onRefresh = { })
        }

        composeTestRule
            .onNodeWithText(message)
            .assertIsDisplayed()
    }

    @Test
    fun testMjImagesScreen() {
        var viewModel: MjImagesViewModel? = null

        composeTestRule.setContent {
            with(initKoinAndMockViewModel(LocalContext.current)) {
                viewModel = this
                MjImagesApp(this)
            }
        }

        composeTestRule
            .waitUntil(3000) {
                viewModel?.state?.value == State.CONTENT
            }

        composeTestRule
            .onNodeWithTag("imagesGrid")
            .assertIsDisplayed()
    }
}
