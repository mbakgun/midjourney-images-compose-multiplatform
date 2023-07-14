package domain.usecase

import data.repository.MjImagesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MjImagesUseCase : KoinComponent {

    private val repository: MjImagesRepository = get()

    suspend fun isEligibleToShowSnackMessage(): Boolean =
        repository.isEligibleToShowSnackMessage()

    suspend fun setSnackMessageShown() {
        repository.setSnackMessageShown()
    }
}
