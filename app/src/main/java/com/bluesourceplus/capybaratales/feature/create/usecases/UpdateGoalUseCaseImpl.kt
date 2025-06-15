package com.bluesourceplus.bluedays.feature.create.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import com.bluesourceplus.capybaratales.data.MoodRepo

class UpdateGoalUseCaseImpl(private val moodRepo: MoodRepo) : UpdateGoalUseCase {
    override suspend fun invoke(goal: MoodModel) {
        moodRepo.update(goal)
    }
}