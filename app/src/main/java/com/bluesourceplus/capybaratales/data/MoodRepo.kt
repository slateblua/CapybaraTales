package com.bluesourceplus.capybaratales.data

import com.bluesourceplus.capybaratales.data.database.MoodEntry
import kotlinx.coroutines.flow.Flow

interface MoodRepo {
    fun getAll(): Flow<List<MoodModel>>

    fun getById(id: Int): Flow<MoodModel>

    suspend fun update(moodModel: MoodModel)

    suspend fun delete(moodEntry: MoodEntry)

    suspend fun add(moodModel: MoodModel)
}