package com.bluesourceplus.bluedays.feature.create.usecases

import com.bluesourceplus.capybaratales.data.MoodModel

interface UpdateGoalUseCase {
    suspend operator fun invoke(goal: MoodModel)
}