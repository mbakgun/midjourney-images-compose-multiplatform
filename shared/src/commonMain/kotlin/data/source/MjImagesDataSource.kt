package data.source

import data.source.remote.model.MjImagesResponse

interface MjImagesDataSource {

    interface Local {

        suspend fun isEligibleToShowSnackMessage(): Boolean

        suspend fun setSnackMessageShown()
    }

    interface Remote {

        suspend fun getImages(page: Int): MjImagesResponse
    }
}
