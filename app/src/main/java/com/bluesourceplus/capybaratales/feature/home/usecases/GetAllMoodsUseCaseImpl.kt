package com.bluesourceplus.bluedays.feature.home.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import com.bluesourceplus.capybaratales.data.MoodRepo
import com.bluesourceplus.capybaratales.feature.home.usecases.GetAllMoodsUseCase
import kotlinx.coroutines.flow.Flow

class GetAllMoodsUseCaseImpl(private val moodRepo: MoodRepo) : GetAllMoodsUseCase {
    override fun invoke(): Flow<List<MoodModel>> {
        return moodRepo.getAll()
    }
}