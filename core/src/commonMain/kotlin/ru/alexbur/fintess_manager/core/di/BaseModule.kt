package ru.alexbur.fintess_manager.core.di

import org.koin.dsl.module
import ru.alexbur.fintess_manager.core.DispatcherProvider
import ru.alexbur.fintess_manager.core.DispatcherProviderImpl

val baseModule = module {
    single<DispatcherProvider> { DispatcherProviderImpl() }
}