package com.mbakgun.mj

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.initKoin
import ui.MjImagesApp

fun main() {
    System.setProperty("apple.awt.application.appearance", "system")
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "MjImages",
        ) {
            MjImagesApp()
        }
    }
}
