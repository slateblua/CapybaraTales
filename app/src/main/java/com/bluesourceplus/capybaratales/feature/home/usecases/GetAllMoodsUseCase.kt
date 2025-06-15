package com.bluesourceplus.capybaratales.feature.home.usecases

import com.bluesourceplus.capybaratales.data.MoodModel
import kotlinx.coroutines.flow.Flow

interface GetAllMoodsUseCase {
    operator fun invoke(): Flow<List<MoodModel>>
}