package ru.alexbur.fintess_manager.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import ru.alexbur.fintess_manager.core.di.baseModule
import ru.alexbur.fintess_manager.feature.calendar.di.calendarModule
import ru.alexbur.fintess_manager.feature.login.di.loginModule

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    modules(apiModule, baseModule)
    modules(
        loginModule,
        calendarModule,
    )
    appDeclaration()
}