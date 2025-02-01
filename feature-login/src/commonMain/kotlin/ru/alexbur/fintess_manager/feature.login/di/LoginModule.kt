package ru.alexbur.fintess_manager.feature.login.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.alexbur.fintess_manager.feature.login.data.api.LoginApi
import ru.alexbur.fintess_manager.feature.login.data.mapper.LoginMapper
import ru.alexbur.fintess_manager.feature.login.data.repository.LoginRepositoryImpl
import ru.alexbur.fintess_manager.feature.login.data.repository.LoginRepositoryMock
import ru.alexbur.fintess_manager.feature.login.domain.interactor.LoginInteractor
import ru.alexbur.fintess_manager.feature.login.domain.repository.LoginRepository
import ru.alexbur.fintess_manager.feature.login.presentation.LoginViewModel

val loginModule = module {
    viewModel { parameters ->
        LoginViewModel(parameters.get(), get())
    }
    singleOf(::LoginApi)
    factoryOf(::LoginMapper)
    factoryOf(::LoginInteractor)
    factoryOf(::LoginRepositoryMock) { bind<LoginRepository>() }
}