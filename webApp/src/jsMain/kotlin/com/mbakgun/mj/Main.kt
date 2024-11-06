package com.mbakgun.mj

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import di.initKoin
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady
import ui.MjImagesApp

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    onWasmReady {
        CanvasBasedWindow("MjImagesApp") {
            MjImagesApp()
        }
        document.getElementById("loader-container")?.remove()
    }
}
