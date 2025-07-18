package com.bluesourceplus.capybaratales.feature.aboutmoodentry.module

import com.bluesourceplus.capybaratales.feature.aboutmoodentry.AboutMoodViewModel
import com.bluesourceplus.capybaratales.feature.aboutmoodentry.usecases.DeleteMoodUseCase
import com.bluesourceplus.capybaratales.feature.aboutmoodentry.usecases.DeleteMoodUseCaseImpl
import com.bluesourceplus.capybaratales.feature.aboutmoodentry.usecases.GetMoodByIdUseCase
import com.bluesourceplus.capybaratales.feature.aboutmoodentry.usecases.GetMoodByIdUseCaseImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val aboutModule = module {
    viewModel { AboutMoodViewModel() }

    single { GetMoodByIdUseCaseImpl(get()) } bind GetMoodByIdUseCase::class

    single { DeleteMoodUseCaseImpl(get()) } bind DeleteMoodUseCase::class
}