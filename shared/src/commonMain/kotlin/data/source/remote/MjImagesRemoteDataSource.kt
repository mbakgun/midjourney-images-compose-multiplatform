package data.source.remote

import data.source.MjImagesDataSource
import data.source.remote.model.MjImagesResponse
import kotlinx.coroutines.withContext
import util.DispatcherProvider

class MjImagesRemoteDataSource constructor(
    private val service: MjImagesService,
    private val dispatcherProvider: DispatcherProvider,
) : MjImagesDataSource.Remote {

    override suspend fun getImages(
        page: Int
    ): MjImagesResponse =
        withContext(dispatcherProvider.io) {
            service.getImages(page)
        }
}
