package domain.mapper

import data.source.remote.model.MjImagesResponse
import domain.model.MjImage
import domain.model.MjImages
import kotlinx.coroutines.withContext
import util.DispatcherProvider

class MjImagesMapper constructor(
    private val dispatcherProvider: DispatcherProvider,
) {

    suspend fun mapMjImages(
        response: MjImagesResponse
    ): MjImages =
        withContext(dispatcherProvider.default) {
            MjImages(
                currentPage = response.currentPage ?: 0,
                images = mapImages(response),
                totalPages = response.totalPages ?: 0
            )
        }

    private fun mapImages(
        response: MjImagesResponse
    ): List<MjImage> =
        response
            .mjImageResponses
            ?.map {
                MjImage(
                    date = it?.date ?: return@map null,
                    imageUrl = it.imageUrl ?: return@map null,
                    ratio = it.ratio ?: return@map null
                )
            }
            ?.filterNotNull()
            .orEmpty()
}
