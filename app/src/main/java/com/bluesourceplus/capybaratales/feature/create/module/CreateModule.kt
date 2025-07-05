package com.bluesourceplus.bluedays.feature.create.module

import com.bluesourceplus.capybaratales.feature.create.CreateMoodMode
import com.bluesourceplus.capybaratales.feature.create.CreateViewModel
import com.bluesourceplus.capybaratales.feature.create.usecases.AddMoodUseCase
import com.bluesourceplus.bluedays.feature.create.usecases.AddMoodUseCaseImpl
import com.bluesourceplus.capybaratales.feature.create.usecases.UpdateMoodUseCase
import com.bluesourceplus.capybaratales.feature.create.usecases.UpdateMoodUseCaseImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val createModule = module {
    viewModel { (mode: CreateMoodMode) -> CreateViewModel(mode) }

    single { AddMoodUseCaseImpl(get()) } bind AddMoodUseCase::class

    single { UpdateMoodUseCaseImpl(get()) } bind UpdateMoodUseCase::class
}