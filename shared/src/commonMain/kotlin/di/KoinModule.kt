package di

import com.russhwolf.settings.Settings
import data.repository.MjImagesRepository
import data.source.MjImagesDataSource
import data.source.local.MjImagesLocalDataSource
import data.source.remote.MjImagesRemoteDataSource
import data.source.remote.MjImagesService
import domain.mapper.MjImagesMapper
import domain.usecase.MjImagesFetchUseCase
import domain.usecase.MjImagesUseCase
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import ui.MjImagesViewModel
import util.getDispatcherProvider

private val utilityModule = module {
    factory { getDispatcherProvider() }
}

private val apiModule = module {
    factory { MjImagesService() }
}

private val repositoryModule = module {
    single { MjImagesRepository() }
    factory { Settings() }
    factory<MjImagesDataSource.Remote> { MjImagesRemoteDataSource(get()) }
    factory<MjImagesDataSource.Local> { MjImagesLocalDataSource(get(), get()) }
}

private val useCaseModule = module {
    factory { MjImagesMapper(get()) }
    factory { MjImagesFetchUseCase() }
    factory { MjImagesUseCase() }
}

private val uiModule = module {
    viewModelOf(::MjImagesViewModel)
}

private val sharedModules =
    listOf(uiModule, useCaseModule, repositoryModule, apiModule, utilityModule)

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    modules(sharedModules)
    appDeclaration()
}

fun initKoin() = initKoin { }
