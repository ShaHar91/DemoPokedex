package be.christiano.demopokedex.di

import android.content.Context
import be.christiano.demopokedex.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

object KoinInitializer {

    fun init(context: Context) {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
            androidContext(context)

            modules(appModule, repoModule, viewModelModule)
        }
    }
}