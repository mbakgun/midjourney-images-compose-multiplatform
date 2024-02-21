package util

import android.content.Context
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.cache.memory.maxSizePercent
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
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
            bitmapMemoryCacheConfig {
                maxSizePercent(appContext, 0.25)
            }
            imageMemoryCacheConfig {
                maxSize(50)
            }
            painterMemoryCacheConfig {
                maxSize(50)
            }

            diskCacheConfig {
                directory(appContext.cacheDir.resolve("image_cache").toOkioPath())
                maxSizeBytes(512L * 1024 * 1024)
            }
        }
    }
}
