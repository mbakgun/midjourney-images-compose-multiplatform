package di

import data.repository.MjImagesRepository
import data.source.MjImagesDataSource
import data.source.remote.MjImagesRemoteDataSource
import data.source.remote.MjImagesService
import domain.mapper.MjImagesMapper
import domain.usecase.MjImagesFetchUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import util.getDispatcherProvider

private val utilityModule = module {
    factory { getDispatcherProvider() }
}

private val apiModule = module {
    factory { MjImagesService() }
}

private val repositoryModule = module {
    single { MjImagesRepository() }
    factory<MjImagesDataSource.Remote> { MjImagesRemoteDataSource(get(), get()) }
}

private val useCaseModule = module {
    factory { MjImagesMapper(get()) }
    factory { MjImagesFetchUseCase() }
}

private val sharedModules = listOf(useCaseModule, repositoryModule, apiModule, utilityModule)

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    modules(sharedModules)
    appDeclaration()
}

fun initKoin() = initKoin { }
