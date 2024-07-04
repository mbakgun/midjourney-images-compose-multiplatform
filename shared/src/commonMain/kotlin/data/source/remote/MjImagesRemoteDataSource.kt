package data.source.remote

import data.source.MjImagesDataSource
import data.source.remote.model.MjImagesResponse

class MjImagesRemoteDataSource(
    private val service: MjImagesService,
) : MjImagesDataSource.Remote {

    override suspend fun getImages(
        page: Int
    ): MjImagesResponse = service.getImages(page)
}
