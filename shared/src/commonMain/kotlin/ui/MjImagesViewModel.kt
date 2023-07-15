package ui

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import domain.model.MjImages
import domain.model.State
import domain.usecase.MjImagesFetchUseCase
import domain.usecase.MjImagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MjImagesViewModel(
    private val fetchUseCase: MjImagesFetchUseCase,
    private val useCase: MjImagesUseCase,
) : KMMViewModel() {

    private val _state = MutableStateFlow(State.LOADING)
    val state: StateFlow<State> = _state

    private val _images = MutableStateFlow(MjImages())
    val images: StateFlow<MjImages> = _images

    private val _useDarkTheme: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val useDarkTheme: StateFlow<Boolean> = _useDarkTheme

    init {
        checkTheme()
        fetchImages()
    }

    fun loadMore() {
        with(_images.value) {
            if (currentPage < totalPages) {
                fetchImages(currentPage + 1)
            }
        }
    }

    fun refreshImages() {
        _images.value = MjImages()
        fetchImages()
    }

    private fun fetchImages(
        page: Int = 1,
    ) {
        fetchUseCase
            .getImages(page)
            .onStart { _state.value = State.LOADING }
            .onEach { images ->
                if (images.isEmpty()) {
                    _state.value = State.EMPTY
                } else {
                    _state.value = State.CONTENT
                    _images.value = _images.value + images
                }
            }.catch {
                _state.value = State.ERROR
            }
            .launchIn(viewModelScope.coroutineScope)
    }

    suspend fun isEligibleToShowSnackBar(): Boolean =
        useCase.isEligibleToShowSnackMessage()

    suspend fun setSnackMessageShown() {
        useCase.setSnackMessageShown()
    }

    fun setDarkMode(isEnabled: Boolean) {
        viewModelScope.coroutineScope.launch {
            _useDarkTheme.emit(isEnabled)
            useCase.setDarkMode(isEnabled)
        }
    }

    private fun checkTheme() {
        viewModelScope.coroutineScope.launch {
            _useDarkTheme.emit(useCase.isDarkModeEnabled())
        }
    }
}
