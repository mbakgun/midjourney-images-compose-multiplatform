package fakes

import data.source.MjImagesDataSource
import data.source.remote.model.MjImageResponse
import data.source.remote.model.MjImagesResponse
import okio.IOException

class EmptyMjImagesDataSource : MjImagesDataSource.Remote {

    override suspend fun getImages(
        page: Int
    ): MjImagesResponse =
        MjImagesResponse(
            currentPage = 0,
            totalPages = 0,
            mjImageResponses = emptyList(),
            pageSize = null,
            totalImages = null,
        )
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
                    imageUrl = "https://mj.akgns.com",
                    ratio = 1.0,
                    hqImageUrl = "https://mj.akgns.com/hq",
                )
            ),
            pageSize = null,
            totalImages = null,
        )
}

class ErrorMjImagesDataSource : MjImagesDataSource.Remote {

    override suspend fun getImages(
        page: Int
    ): MjImagesResponse =
        throw IOException("Error")

}
