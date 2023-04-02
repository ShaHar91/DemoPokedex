package be.christiano.demopokedex

import android.app.Application
import be.christiano.demopokedex.di.KoinInitializer

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        KoinInitializer.init(this)
    }
}