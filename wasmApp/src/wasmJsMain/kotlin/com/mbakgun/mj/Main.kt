package com.mbakgun.mj

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import di.initKoin
import kotlinx.browser.document
import ui.MjImagesApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()

    ComposeViewport("MjImagesApp") {
        MjImagesApp()
    }
    document.getElementById("loader-container")?.remove()
}
