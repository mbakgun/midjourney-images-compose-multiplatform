package util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.util.DebugLogger
import com.seiko.imageloader.util.LogPriority
import okio.Path.Companion.toOkioPath

@Composable
actual fun generateImageLoader(): ImageLoader {
    val context = LocalContext.current

    return ImageLoader {
        logger = DebugLogger(LogPriority.DEBUG)
        components {
            setupDefaultComponents(context)
        }
        interceptor {
            diskCacheConfig {
                directory(context.cacheDir.resolve("image_cache").toOkioPath())
                maxSizeBytes(1024 * 1024 * 100)
            }
        }
    }
}
