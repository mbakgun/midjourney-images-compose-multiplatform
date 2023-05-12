package util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import okio.Path.Companion.toOkioPath

@Composable
actual fun generateImageLoader(): ImageLoader {
    val context = LocalContext.current

    return ImageLoader {
        components {
            setupDefaultComponents(context)
        }
        interceptor {

            memoryCacheConfig {
                maxSizePercent(context, 0.55)
            }

            diskCacheConfig {
                directory(context.cacheDir.resolve("image_cache").toOkioPath())
                maxSizeBytes(1024 * 1024 * 100)
            }
        }
    }
}
