package util

import android.content.Context
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.defaultImageResultMemoryCache
import com.seiko.imageloader.option.androidContext
import okio.Path.Companion.toOkioPath

lateinit var appContext: Context

actual fun generateImageLoader(): ImageLoader {
    return ImageLoader {
        options {
            playAnimate = false
            premultipliedAlpha = false
            retryIfDiskDecodeError = false
            androidContext(appContext)
        }
        components {
            setupDefaultComponents(appContext)
        }
        interceptor {
            defaultImageResultMemoryCache()
            memoryCacheConfig {
                maxSizePercent(appContext, 0.25)
            }
            diskCacheConfig {
                directory(appContext.cacheDir.resolve("image_cache").toOkioPath())
                maxSizeBytes(512L * 1024 * 1024)
            }
        }
    }
}
