package ru.alexbur.fintess_manager.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import ru.alexbur.fintess_manager.common_presentation.di.presentationModule
import ru.alexbur.fintess_manager.core.di.baseModule
import ru.alexbur.fintess_manager.feature.calendar.di.calendarModule
import ru.alexbur.fintess_manager.feature.login.di.loginModule
import ru.alexbur.fintess_manager.network.di.apiModule

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    modules(apiModule, baseModule, presentationModule)
    modules(
        loginModule,
        calendarModule,
        appModule,
    )
    appDeclaration()
}