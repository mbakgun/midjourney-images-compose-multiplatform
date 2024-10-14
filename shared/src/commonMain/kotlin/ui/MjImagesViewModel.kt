package ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.MjImages
import domain.model.State
import domain.usecase.MjImagesFetchUseCase
import domain.usecase.MjImagesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import midjourneyimagescomposemultiplatform.shared.generated.resources.Res
import midjourneyimagescomposemultiplatform.shared.generated.resources.failed_fetch_message
import org.jetbrains.compose.resources.getString

class MjImagesViewModel(
    private val fetchUseCase: MjImagesFetchUseCase,
    private val useCase: MjImagesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(State.LOADING)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _images = MutableStateFlow(MjImages())
    val images: StateFlow<MjImages> = _images.asStateFlow()

    private val _useDarkTheme: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val useDarkTheme: StateFlow<Boolean> = _useDarkTheme.asStateFlow()

    private val _dialogPreviewUrl: MutableStateFlow<String> = MutableStateFlow("")
    val dialogPreviewUrl: StateFlow<String> = _dialogPreviewUrl.asStateFlow()

    private val _snackMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val snackMessage: SharedFlow<String> = _snackMessage.asSharedFlow()

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
        viewModelScope.launch {
            useCase.clearImages()
            _images.value = MjImages()
            fetchImages()
        }
    }

    private fun fetchImages(
        page: Int = 1,
    ) {
        fetchUseCase
            .getImages(page)
            .onStart { _state.value = State.LOADING }
            .onEach { images ->
                if (_images.value.images.isEmpty() && images.isEmpty()) {
                    _state.value = State.EMPTY
                } else {
                    _state.value = State.CONTENT
                    _images.value += images
                }
            }.catch {
                it.printStackTrace()
                if (_images.value.isEmpty()) {
                    _state.value = State.ERROR
                } else {
                    _snackMessage.emit(getString(Res.string.failed_fetch_message))
                }
            }
            .launchIn(viewModelScope)
    }

    suspend fun isEligibleToShowSnackBar(): Boolean =
        useCase.isEligibleToShowSnackMessage() && _images.value.images.isNotEmpty()

    suspend fun setSnackMessageShown() {
        useCase.setSnackMessageShown()
    }

    fun setDarkMode(isEnabled: Boolean) {
        viewModelScope.launch {
            _useDarkTheme.emit(isEnabled)
            useCase.setDarkMode(isEnabled)
        }
    }

    private fun checkTheme() {
        viewModelScope.launch {
            _useDarkTheme.emit(useCase.isDarkModeEnabled())
        }
    }

    fun showPreviewDialog(hqImageUrl: String) {
        viewModelScope.launch {
            _dialogPreviewUrl.emit(hqImageUrl)
        }
    }

    fun dismissPreviewDialog() {
        viewModelScope.launch {
            _dialogPreviewUrl.emit("")
        }
    }
}
