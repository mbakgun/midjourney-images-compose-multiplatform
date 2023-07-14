package ui

import android.content.Context
import data.source.MjImagesDataSource
import data.source.remote.model.MjImageResponse
import data.source.remote.model.MjImagesResponse
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.io.IOException

// init koin - mock response - return viewModel
fun initKoinAndMockViewModel(
    context: Context,
    dataSource: MjImagesDataSource.Remote? = null
): MjImagesViewModel =
    initKoin {
        androidContext(androidContext = context)
        if (dataSource != null) {
            modules(
                module { factory { dataSource } },
                module { viewModel { MjImagesViewModel(get(), get()) } }
            )
        } else {
            modules(
                module { viewModel { MjImagesViewModel(get(), get()) } }
            )
        }
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
