package fakes

import data.source.MjImagesDataSource
import data.source.remote.model.MjImagesResponse

class MjImagesLocalFakeDataSource : MjImagesDataSource.Local {

    override suspend fun isEligibleToShowSnackMessage(): Boolean = true

    override suspend fun setSnackMessageShown() = Unit

    override suspend fun isDarkModeEnabled(): Boolean = false

    override suspend fun setDarkMode(enabled: Boolean) = Unit

    override suspend fun isCacheValid(): Boolean = false

    override suspend fun getImages(page: Int): MjImagesResponse? = null

    override suspend fun clearImages() = Unit

    override suspend fun cacheResponse(
        page: Int,
        response: MjImagesResponse
    ) = Unit
}
