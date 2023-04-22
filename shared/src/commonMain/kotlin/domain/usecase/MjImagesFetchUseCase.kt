package domain.usecase

import data.repository.MjImagesRepository
import domain.mapper.MjImagesMapper
import domain.model.MjImages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MjImagesFetchUseCase : KoinComponent {

    private val repository: MjImagesRepository = get()
    private val mapper: MjImagesMapper = get()

    fun getImages(page: Int): Flow<MjImages> =
        repository
            .getImages(page = page)
            .map(mapper::mapMjImages)
}
