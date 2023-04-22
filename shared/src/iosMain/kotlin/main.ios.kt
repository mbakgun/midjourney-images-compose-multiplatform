import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import ui.MjImagesApp
import ui.MjImagesViewModel

fun MainViewController(viewModel: MjImagesViewModel): UIViewController =
    ComposeUIViewController {
        MjImagesApp(viewModel)
    }
