package data.source.remote

import data.source.remote.model.MjImagesResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MjImagesService : KtorApi() {

    suspend fun getImages(
        page: Int,
    ): MjImagesResponse =
        client.get {
            apiUrl("images")
            parameter("page", page)
        }.body()
}
