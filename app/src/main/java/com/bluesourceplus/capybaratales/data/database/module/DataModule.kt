package com.bluesourceplus.capybaratales.data.database.module

import androidx.room.Room
import com.bluesourceplus.capybaratales.data.MoodRepo
import com.bluesourceplus.capybaratales.data.MoodRepoImpl
import com.bluesourceplus.capybaratales.data.database.GoalDatabase
import com.bluesourceplus.capybaratales.data.database.LocalDataSource
import com.bluesourceplus.capybaratales.data.database.RoomLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

const val DATABASE_NAME = "goal-database"

val dataModule = module {
    single { get<GoalDatabase>().getMoodDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            GoalDatabase::class.java,
            DATABASE_NAME,
        ).fallbackToDestructiveMigration()
            .build()
    }

    // Binds RoomLocalDataSource to LocalDataSource
    single { RoomLocalDataSource(get()) } bind LocalDataSource::class

    // Binds GoalRepoImpl to GoalRepo
    single { MoodRepoImpl(get()) } bind MoodRepo::class
}