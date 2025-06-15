package com.bluesourceplus.capybaratales.feature.aboutmoodentry.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import kotlinx.coroutines.flow.Flow

interface GetMoodByIdUseCase {
    operator fun invoke(id: Int): Flow<MoodModel>
}