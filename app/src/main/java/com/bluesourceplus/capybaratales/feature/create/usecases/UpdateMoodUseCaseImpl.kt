package com.bluesourceplus.capybaratales.feature.create.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import com.bluesourceplus.capybaratales.data.MoodRepo

class UpdateMoodUseCaseImpl(private val moodRepo: MoodRepo) : UpdateMoodUseCase {
    override suspend fun invoke(mood: MoodModel) {
        moodRepo.update(mood)
    }
}