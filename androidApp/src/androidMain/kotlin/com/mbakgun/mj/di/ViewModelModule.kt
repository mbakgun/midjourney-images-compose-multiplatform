package com.mbakgun.mj.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ui.MjImagesViewModel

val viewModelModule = module {
    viewModelOf(::MjImagesViewModel)
}
