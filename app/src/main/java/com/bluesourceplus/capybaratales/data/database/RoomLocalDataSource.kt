package com.bluesourceplus.capybaratales.data.database

import com.bluesourceplus.capybaratales.data.MoodModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalDataSource (private val dao: MoodDao): LocalDataSource {
    override fun getAll(): Flow<List<MoodModel>> {
        return dao.getAll().map { goalFlow ->
            goalFlow.map { goalEnt ->
                goalEnt.toModel()
            }
        }
    }

    override fun getById(id: Int): Flow<MoodModel> {
        return dao.getById(id).map { goalEnt ->
            goalEnt.toModel()
        }
    }

    override suspend fun update(moodModel: MoodModel) {
        dao.update(moodEntry = moodModel.toEntry())
    }

    override suspend fun delete(moodEntry: MoodEntry) {
        dao.delete(moodEntry)
    }

    override suspend fun add(moodModel: MoodModel) {
        dao.add(moodEntry = moodModel.toEntry())
    }
}