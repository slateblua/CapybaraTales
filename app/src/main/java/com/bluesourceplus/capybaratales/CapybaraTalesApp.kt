package com.bluesourceplus.capybaratales

import android.app.Application
import com.bluesourceplus.capybaratales.data.database.module.dataModule
import com.bluesourceplus.capybaratales.feature.aboutmoodentry.module.aboutModule
import com.bluesourceplus.bluedays.feature.create.module.createModule
import com.bluesourceplus.capybaratales.feature.home.module.homeModule
import com.bluesourceplus.capybaratales.feature.preferences.module.preferencesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CapybaraTalesApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin here, only once
        startKoin {
            androidContext(this@CapybaraTalesApp)
            androidLogger()
            modules(
                homeModule,
                dataModule,
                createModule,
                preferencesModule,
                aboutModule,
            )
        }
    }
}