package ru.alexbur.fintess_manager.di

import org.koin.dsl.module
import ru.alexbur.fintess_manager.base.DispatcherProvider
import ru.alexbur.fintess_manager.base.DispatcherProviderImpl

val baseModule = module {
    single<DispatcherProvider> { DispatcherProviderImpl() }
}