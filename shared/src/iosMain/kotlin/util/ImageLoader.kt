package util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import okio.FileSystem

@Composable
actual fun generateImageLoader(): ImageLoader =
    ImageLoader(
        requestCoroutineContext = rememberCoroutineScope().coroutineContext,
    ) {
        components {
            setupDefaultComponents(imageScope)
        }
        interceptor {

            memoryCacheConfig {
                maxSizePercent(0.55)
            }

            diskCacheConfig {
                directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY)
                maxSizeBytes(1024 * 1024 * 100)
            }
        }
    }