package data.source

import data.source.remote.model.MjImagesResponse

interface MjImagesDataSource {

    interface Local {

        suspend fun isEligibleToShowSnackMessage(): Boolean
        suspend fun setSnackMessageShown()
        suspend fun isDarkModeEnabled(): Boolean
        suspend fun setDarkMode(enabled: Boolean)
    }

    interface Remote {

        suspend fun getImages(page: Int): MjImagesResponse
    }
}
