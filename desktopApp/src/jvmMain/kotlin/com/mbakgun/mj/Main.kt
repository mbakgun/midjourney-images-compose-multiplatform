package com.mbakgun.mj

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.initKoin
import domain.usecase.MjImagesFetchUseCase
import domain.usecase.MjImagesUseCase
import org.koin.java.KoinJavaComponent.inject
import ui.MjImagesApp
import ui.MjImagesViewModel

fun main() {
    System.setProperty("apple.awt.application.appearance", "system")
    initKoin { }
    val fetchUseCase by inject<MjImagesFetchUseCase>(MjImagesFetchUseCase::class.java)
    val useCase by inject<MjImagesUseCase>(MjImagesUseCase::class.java)
    val viewModel = MjImagesViewModel(fetchUseCase, useCase)

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "MjImages",
            onKeyEvent = {
                when (it.key) {
                    Key.R -> {
                        viewModel.refreshImages()
                        true
                    }

                    else -> false
                }
            }
        ) {
            MjImagesApp(viewModel)
        }
    }
}
