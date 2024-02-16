package util

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents

actual fun generateImageLoader(): ImageLoader {
    return ImageLoader {
        components {
            setupDefaultComponents()
        }
        interceptor {
            memoryCacheConfig {
                maxSizeBytes(512 * 1024 * 1024) // 512MB
            }
        }
    }
}
