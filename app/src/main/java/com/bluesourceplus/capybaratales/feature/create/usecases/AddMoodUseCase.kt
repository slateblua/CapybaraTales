package com.bluesourceplus.capybaratales.feature.create.usecases

import com.bluesourceplus.capybaratales.data.MoodModel

interface AddMoodUseCase {
    suspend operator fun invoke(moodModel: MoodModel)
}