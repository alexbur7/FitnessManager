package ru.alexbur.fintess_manager

import android.app.Application
import ru.alexbur.fintess_manager.di.initKoin

class FitnessManagerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}