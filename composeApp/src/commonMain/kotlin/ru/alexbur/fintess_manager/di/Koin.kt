package ru.alexbur.fintess_manager.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import ru.alexbur.fintess_manager.core.di.baseModule

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    modules(apiModule, baseModule, rocketsModule)
    appDeclaration()
}