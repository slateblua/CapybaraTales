package com.bluesourceplus.capybaratales.feature.aboutmoodentry.usecases

import com.bluesourceplus.capybaratales.data.MoodModel

interface DeleteMoodUseCase {
    suspend operator fun invoke(goal: MoodModel)
}