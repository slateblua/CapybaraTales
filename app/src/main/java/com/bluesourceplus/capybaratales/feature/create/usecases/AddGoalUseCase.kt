package com.bluesourceplus.bluedays.feature.create.usecases

import com.bluesourceplus.capybaratales.data.MoodModel

interface AddGoalUseCase {
    suspend operator fun invoke(moodModel: MoodModel)
}