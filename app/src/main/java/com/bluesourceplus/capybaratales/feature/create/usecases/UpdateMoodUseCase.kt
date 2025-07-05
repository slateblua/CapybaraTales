package com.bluesourceplus.capybaratales.feature.create.usecases

import com.bluesourceplus.capybaratales.data.MoodModel

interface UpdateMoodUseCase {
    suspend operator fun invoke(mood: MoodModel)
}