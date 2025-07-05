package com.bluesourceplus.capybaratales.feature.home.module

import com.bluesourceplus.capybaratales.feature.home.HomeViewModel
import com.bluesourceplus.capybaratales.feature.home.usecases.GetAllMoodsUseCase
import com.bluesourceplus.capybaratales.feature.home.usecases.GetAllMoodsUseCaseImpl
import com.bluesourceplus.capybaratales.feature.home.usecases.UpdateMoodUseCase
import com.bluesourceplus.capybaratales.feature.home.usecases.UpdateMoodUseCaseImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel() }

    single { GetAllMoodsUseCaseImpl(get()) } bind GetAllMoodsUseCase::class

    single { UpdateMoodUseCaseImpl(get()) } bind UpdateMoodUseCase::class
}