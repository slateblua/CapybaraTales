package com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import com.bluesourceplus.capybaratales.data.MoodRepo
import com.bluesourceplus.capybaratales.feature.aboutmoodentry.usecases.GetMoodByIdUseCase
import kotlinx.coroutines.flow.Flow

class GetMoodByIdUseCaseImpl(private val moodRepo: MoodRepo) : GetMoodByIdUseCase {
    override fun invoke(id: Int): Flow<MoodModel> {
        return moodRepo.getById(id)
    }
}