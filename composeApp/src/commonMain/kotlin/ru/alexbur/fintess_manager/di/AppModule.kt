package ru.alexbur.fintess_manager.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.alexbur.fintess_manager.presentation.app.AppViewModel

val appModule = module {
    viewModelOf(::AppViewModel)
}