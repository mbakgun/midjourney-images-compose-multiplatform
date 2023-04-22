package data.source

import data.source.remote.model.MjImagesResponse

interface MjImagesDataSource {

    interface Remote {

        suspend fun getImages(page: Int): MjImagesResponse
    }
}
