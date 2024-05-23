package util

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.request.CachePolicy
import coil3.util.DebugLogger
import okio.FileSystem

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.logger(DebugLogger()).build()

fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(512L * 1024 * 1024)
        .build()
}
