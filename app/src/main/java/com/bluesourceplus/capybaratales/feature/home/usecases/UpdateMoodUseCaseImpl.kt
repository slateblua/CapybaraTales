package com.bluesourceplus.capybaratales.feature.home.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import com.bluesourceplus.capybaratales.data.MoodRepo

class UpdateMoodUseCaseImpl(private val moodRepo: MoodRepo) : UpdateMoodUseCase {
    override suspend fun invoke(moodModel: MoodModel) {
        moodRepo.update(moodModel)
    }
}