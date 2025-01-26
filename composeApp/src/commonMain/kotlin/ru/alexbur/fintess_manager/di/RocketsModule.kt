package ru.alexbur.fintess_manager.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.alexbur.fintess_manager.data.api.FitnessManagerApi
import ru.alexbur.fintess_manager.data.mappers.RocketMappers
import ru.alexbur.fintess_manager.data.repository.RocketsRepositoryImpl
import ru.alexbur.fintess_manager.domain.interactor.RocketsInteractor
import ru.alexbur.fintess_manager.domain.repository.RocketsRepository
import ru.alexbur.fintess_manager.presentation.AppViewModel

internal val rocketsModule = module {
    single { RocketMappers() }
    singleOf(::RocketsRepositoryImpl) { bind<RocketsRepository>() }
    single {
        FitnessManagerApi(get())
    }
    single { RocketsInteractor(get()) }
    viewModelOf(::AppViewModel)
}