package com.bluesourceplus.capybaratales.feature.aboutmoodentry.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import com.bluesourceplus.capybaratales.data.MoodRepo
import com.bluesourceplus.capybaratales.data.database.toEntry

class DeleteMoodUseCaseImpl(private val moodRepo: MoodRepo) : DeleteMoodUseCase {
    override suspend fun invoke(mood: MoodModel) {
        moodRepo.delete(mood.toEntry())
    }
}