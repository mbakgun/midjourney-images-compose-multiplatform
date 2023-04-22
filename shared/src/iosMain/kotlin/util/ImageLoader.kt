package util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.util.DebugLogger
import com.seiko.imageloader.util.LogPriority
import okio.FileSystem

@Composable
actual fun generateImageLoader(): ImageLoader =
    ImageLoader(
        requestCoroutineContext = rememberCoroutineScope().coroutineContext
    ) {
        logger = DebugLogger(LogPriority.DEBUG)
        components {
            setupDefaultComponents(imageScope)
        }
        interceptor {
            diskCacheConfig {
                directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY)
                maxSizeBytes(1024 * 1024 * 100)
            }
        }
    }
