package com.bluesourceplus.capybaratales.feature.home.usecases

import com.bluesourceplus.capybaratales.data.MoodModel

interface UpdateMoodUseCase {
    suspend operator fun invoke(moodModel: MoodModel)
}