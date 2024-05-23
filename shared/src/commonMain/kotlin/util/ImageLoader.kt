package util

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.request.CachePolicy
import coil3.util.DebugLogger

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED).logger(DebugLogger()).build()
