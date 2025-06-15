package com.bluesourceplus.capybaratales.feature.preferences.module

import com.bluesourceplus.capybaratales.feature.preferences.PreferencesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val preferencesModule = module {
    viewModel { PreferencesViewModel() }
}