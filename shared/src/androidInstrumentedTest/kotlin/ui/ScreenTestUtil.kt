package ui

import android.content.Context
import data.source.MjImagesDataSource
import data.source.remote.model.MjImageResponse
import data.source.remote.model.MjImagesResponse
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import java.io.IOException

// setAppContext for ImageLoader &-init koin - mock response - return viewModel
fun initAppAndMockViewModel(
    context: Context,
    dataSource: MjImagesDataSource.Remote? = null
): MjImagesViewModel {
    return initKoin {
        androidContext(context)
        modules(
            module {
                dataSource?.let { factory { it } }
                viewModelOf(::MjImagesViewModel)
            }
        )
    }.koin.get()
}

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
    ): MjImagesResponse =
        MjImagesResponse(
            currentPage = 0,
            totalPages = 0,
            mjImageResponses = null,
            pageSize = null,
            totalImages = null,
        )
}

class ErrorMjImagesDataSource : MjImagesDataSource.Remote {

    override suspend fun getImages(
        page: Int
    ): MjImagesResponse =
        throw IOException("Unknown")
}
