package com.mbakgun.mj

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import di.initKoin
import domain.usecase.MjImagesFetchUseCase
import domain.usecase.MjImagesUseCase
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady
import ui.MjImagesApp
import ui.MjImagesViewModel

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val koin = initKoin { }.koin
    val fetchUseCase = koin.get<MjImagesFetchUseCase>()
    val useCase = koin.get<MjImagesUseCase>()
    val viewModel = MjImagesViewModel(fetchUseCase, useCase)

    onWasmReady {
        CanvasBasedWindow("MjImagesApp") {
            MjImagesApp(viewModel)
        }
        document.getElementById("loader-container")?.remove()
    }
}
