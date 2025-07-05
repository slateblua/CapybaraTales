package com.bluesourceplus.bluedays.feature.create.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import com.bluesourceplus.capybaratales.data.MoodRepo
import com.bluesourceplus.capybaratales.feature.create.usecases.AddMoodUseCase

class AddMoodUseCaseImpl (private val moodRepo: MoodRepo) : AddMoodUseCase {
    override suspend operator fun invoke(moodModel: MoodModel) {
        moodRepo.add(moodModel)
    }
}