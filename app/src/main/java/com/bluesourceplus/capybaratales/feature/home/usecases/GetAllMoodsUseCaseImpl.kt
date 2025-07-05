package com.bluesourceplus.capybaratales.feature.home.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import com.bluesourceplus.capybaratales.data.MoodRepo
import kotlinx.coroutines.flow.Flow

class GetAllMoodsUseCaseImpl(private val moodRepo: MoodRepo) : GetAllMoodsUseCase {
    override fun invoke(): Flow<List<MoodModel>> {
        return moodRepo.getAll()
    }
}