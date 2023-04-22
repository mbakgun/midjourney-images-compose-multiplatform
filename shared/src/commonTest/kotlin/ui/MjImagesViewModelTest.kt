package ui

import app.cash.turbine.test
import data.source.MjImagesDataSource
import di.initKoin
import domain.model.State
import fakes.ErrorMjImagesDataSource
import fakes.SuccessMjImagesDataSource
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import util.getDispatcherProvider

@OptIn(ExperimentalCoroutinesApi::class)
class MjImagesViewModelTest : KoinTest {

    @BeforeTest
    fun setUp() {
        initKoin {
            modules(module {
                factory { MjImagesViewModel(get()) }
            })
        }
        Dispatchers.setMain(getDispatcherProvider().unconfined)
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetch images gets success then state should be updated`() = runTest {
        // given
        setupDataSource(SuccessMjImagesDataSource())

        // when
        val viewModel = get<MjImagesViewModel>()

        // then
        viewModel.state.test {
            assertEquals(State.CONTENT, awaitItem())
        }
    }

    @Test
    fun `when fetch images gets success then images should be updated`() = runTest {
        // given
        setupDataSource(SuccessMjImagesDataSource())

        // when
        val viewModel = get<MjImagesViewModel>()

        // then
        viewModel.images.test {
            assertEquals(1, awaitItem().images.size)
        }
    }

    @Test
    fun `when fetch images called then initial state should be loading`() = runTest {
        // given
        setupDataSource(SuccessMjImagesDataSource())

        // when
        val viewModel = get<MjImagesViewModel>()

        // then
        assertEquals(State.LOADING, viewModel.state.value)
    }

    @Test
    fun `when refresh images called images should be empty`() = runTest {
        // given
        setupDataSource(SuccessMjImagesDataSource())

        // when
        val viewModel = get<MjImagesViewModel>()

        // then
        viewModel.images.test {
            assertEquals(1, awaitItem().images.size)

            viewModel.refreshImages()

            assertEquals(0, awaitItem().images.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when load more called with page=maxPage then nothing happens`() = runTest {
        // given
        setupDataSource(SuccessMjImagesDataSource())

        // when
        val viewModel = get<MjImagesViewModel>()

        // then
        viewModel.state.test {
            assertEquals(State.CONTENT, awaitItem())

            viewModel.loadMore()

            expectNoEvents()
        }
    }

    @Test
    fun `when fetch images gets error then state should be updated`() = runTest {
        // given
        setupDataSource(ErrorMjImagesDataSource())

        // when
        val viewModel = get<MjImagesViewModel>()

        // then
        viewModel.state.test {
            assertEquals(State.ERROR, awaitItem())
        }
    }

    private fun setupDataSource(
        dataSource: MjImagesDataSource.Remote
    ) {
        loadKoinModules(module {
            factory { dataSource }
        })
    }
}