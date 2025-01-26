package ru.alexbur.fintess_manager.feature.login.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.alexbur.fintess_manager.feature.login.presentation.LoginViewModel

val loginModule = module {
    viewModelOf(::LoginViewModel)
}