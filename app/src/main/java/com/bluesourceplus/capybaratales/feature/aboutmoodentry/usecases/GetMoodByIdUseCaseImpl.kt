package com.bluesourceplus.capybaratales.feature.aboutmoodentry.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import com.bluesourceplus.capybaratales.data.MoodRepo
import kotlinx.coroutines.flow.Flow

class GetMoodByIdUseCaseImpl(private val moodRepo: MoodRepo) : GetMoodByIdUseCase {
    override fun invoke(id: Int): Flow<MoodModel> {
        return moodRepo.getById(id)
    }
}