package com.bluesourceplus.bluedays.feature.create.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import com.bluesourceplus.capybaratales.data.MoodRepo

class AddGoalUseCaseImpl (private val moodRepo: MoodRepo) : AddGoalUseCase {
    override suspend operator fun invoke(moodModel: MoodModel) {
        moodRepo.add(moodModel)
    }
}