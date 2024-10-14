package ui

import android.content.Context
import data.source.MjImagesDataSource
import data.source.remote.model.MjImageResponse
import data.source.remote.model.MjImagesResponse
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import java.io.IOException

// setAppContext for ImageLoader &-init koin - mock response - return viewModel
fun initAppAndMockViewModel(
    context: Context,
    remoteDataSource: MjImagesDataSource.Remote? = null,
    localDataSource: MjImagesDataSource.Local? = null
): MjImagesViewModel = initKoin {
    androidContext(androidContext = context)
    if (remoteDataSource != null) modules(module { factory { remoteDataSource } })
    if (localDataSource != null) modules(module { factory { localDataSource } })
    loadKoinModules(module { viewModelOf(::MjImagesViewModel) })
}.koin.get()

class SuccessMjImagesDataSource : MjImagesDataSource.Remote {

    override suspend fun getImages(
        page: Int
    ): MjImagesResponse =
        MjImagesResponse(
            currentPage = 1,
            totalPages = 1,
            mjImageResponses = listOf(
                MjImageResponse(
                    date = "2023-21-04",
                    imageUrl = "",
                    ratio = 1.0,
                    hqImageUrl = ""
                )
            ),
            pageSize = null,
            totalImages = null,
        )
}

class EmptyMjImagesDataSource : MjImagesDataSource.Remote {

    override suspend fun getImages(
        page: Int
    ): MjImagesResponse = MjImagesResponse()
}

class ErrorMjImagesDataSource : MjImagesDataSource.Remote {

    override suspend fun getImages(
        page: Int
    ): MjImagesResponse =
        throw IOException("Unknown")
}

class OfflineMjImagesLocalDataSource : MjImagesDataSource.Local {

    override suspend fun isEligibleToShowSnackMessage(): Boolean = false

    override suspend fun setSnackMessageShown() = Unit

    override suspend fun isDarkModeEnabled(): Boolean = false

    override suspend fun setDarkMode(enabled: Boolean) = Unit

    override suspend fun isCacheValid(): Boolean = true

    override suspend fun getImages(page: Int): MjImagesResponse =
        MjImagesResponse(
            currentPage = 1,
            totalPages = 1,
            mjImageResponses = listOf(
                MjImageResponse(
                    date = "",
                    imageUrl = "",
                    ratio = 1.0,
                    hqImageUrl = ""
                )
            ),
            pageSize = null,
            totalImages = null,
        )

    override suspend fun clearImages() = Unit

    override suspend fun cacheResponse(page: Int, response: MjImagesResponse) = Unit
}
