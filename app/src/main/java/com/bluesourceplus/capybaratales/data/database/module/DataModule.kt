package com.bluesourceplus.capybaratales.data.database.module

import androidx.room.Room
import com.bluesourceplus.capybaratales.data.MoodRepoImpl
import com.bluesourceplus.capybaratales.data.database.LocalDataSource
import com.bluesourceplus.capybaratales.data.database.MoodDatabase
import com.bluesourceplus.capybaratales.data.database.RoomLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

const val DATABASE_NAME = "mood-database"

val dataModule = module {
    single { get<MoodDatabase>().getMoodDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            MoodDatabase::class.java,
            DATABASE_NAME,
        ).fallbackToDestructiveMigration()
            .build()
    }

    // Binds RoomLocalDataSource to LocalDataSource
    single { RoomLocalDataSource(get()) } bind LocalDataSource::class

    // Binds MoodRepoImpl to MoodRepoImpl
    single { MoodRepoImpl(get()) } bind MoodRepoImpl::class
}