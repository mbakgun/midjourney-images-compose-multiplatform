package data.repository

import data.source.MjImagesDataSource
import data.source.remote.model.MjImagesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MjImagesRepository : KoinComponent {

    private val localSource: MjImagesDataSource.Local = get()
    private val remoteSource: MjImagesDataSource.Remote = get()

    fun getImages(
        page: Int,
    ): Flow<MjImagesResponse> = flow {
        if (localSource.isCacheValid()) {
            val imagesCache = localSource.getImages(page)
            imagesCache?.let { emit(it) }
        }

        val mjImagesResponse = remoteSource.getImages(page)
        localSource.cacheResponse(page,mjImagesResponse)
        emit(mjImagesResponse)
    }

    suspend fun isEligibleToShowSnackMessage(): Boolean =
        localSource.isEligibleToShowSnackMessage()

    suspend fun setSnackMessageShown() {
        localSource.setSnackMessageShown()
    }

    suspend fun isDarkModeEnabled(): Boolean =
        localSource.isDarkModeEnabled()

     suspend fun setDarkMode(enabled: Boolean) {
        localSource.setDarkMode(enabled)
    }
}
