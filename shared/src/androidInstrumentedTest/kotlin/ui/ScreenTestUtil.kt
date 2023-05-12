package ui

import android.content.Context
import data.source.MjImagesDataSource
import data.source.remote.model.MjImageResponse
import data.source.remote.model.MjImagesResponse
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// init koin - mock response - return viewModel
fun initKoinAndMockViewModel(context: Context): MjImagesViewModel =
    initKoin {
        androidContext(androidContext = context)
        modules(
            module { factory<MjImagesDataSource.Remote> { SuccessMjImagesDataSource() } },
            module { viewModel { MjImagesViewModel(get()) } }
        )
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
