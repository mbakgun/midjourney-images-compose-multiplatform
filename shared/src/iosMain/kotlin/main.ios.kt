import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ui.MjImagesApp

fun MainViewController(): UIViewController =
    ComposeUIViewController {
        MjImagesApp()
    }
