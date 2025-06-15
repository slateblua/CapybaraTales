package com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import com.bluesourceplus.capybaratales.data.MoodRepo
import com.bluesourceplus.capybaratales.data.database.toEntry
import com.bluesourceplus.capybaratales.feature.aboutmoodentry.usecases.DeleteMoodUseCase

class DeleteMoodUseCaseImpl(private val moodRepo: MoodRepo) : DeleteMoodUseCase {
    override suspend fun invoke(goal: MoodModel) {
        moodRepo.delete(goal.toEntry())
    }
}