package com.bluesourceplus.capybaratales.data

import com.bluesourceplus.capybaratales.data.database.MoodEntry
import com.bluesourceplus.capybaratales.data.database.LocalDataSource
import kotlinx.coroutines.flow.Flow

class MoodRepoImpl (private val localDataSource: LocalDataSource) : MoodRepo {
    override fun getAll(): Flow<List<MoodModel>> {
        return localDataSource.getAll()
    }

    override fun getById(id: Int): Flow<MoodModel> {
        return localDataSource.getById(id)
    }
    override suspend fun update(moodModel: MoodModel) {
        localDataSource.update(moodModel)
    }

    override suspend fun delete(moodEntry: MoodEntry) {
        localDataSource.delete(moodEntry)
    }

    override suspend fun add(moodModel: MoodModel) {
        localDataSource.add(moodModel)
    }
}