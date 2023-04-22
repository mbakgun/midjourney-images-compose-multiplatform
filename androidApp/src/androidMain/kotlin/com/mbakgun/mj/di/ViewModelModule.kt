package com.mbakgun.mj.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ui.MjImagesViewModel

val viewModelModule = module {
    viewModel { MjImagesViewModel(get()) }
}
