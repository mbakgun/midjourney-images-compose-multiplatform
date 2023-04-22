package data.repository

import data.source.MjImagesDataSource
import data.source.remote.model.MjImagesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MjImagesRepository : KoinComponent {

    private val remoteSource: MjImagesDataSource.Remote = get()

    fun getImages(
        page: Int,
    ): Flow<MjImagesResponse> = flow {
        emit(
            remoteSource.getImages(page)
        )
    }
}
