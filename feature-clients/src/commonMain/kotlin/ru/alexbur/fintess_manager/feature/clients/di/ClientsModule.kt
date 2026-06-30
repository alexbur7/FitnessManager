package ru.alexbur.fintess_manager.feature.clients.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.alexbur.fintess_manager.feature.clients.data.api.ClientsApi
import ru.alexbur.fintess_manager.feature.clients.data.mapper.ClientsMapper
import ru.alexbur.fintess_manager.feature.clients.data.repository.ClientsRepositoryImpl
import ru.alexbur.fintess_manager.feature.clients.domain.interactor.ClientsInteractor
import ru.alexbur.fintess_manager.feature.clients.domain.repository.ClientsRepository
import ru.alexbur.fintess_manager.feature.clients.presentation.ClientsViewModel

val clientsModule = module {
    singleOf(::ClientsApi)
    factoryOf(::ClientsMapper)
    factoryOf(::ClientsInteractor)
    factoryOf(::ClientsRepositoryImpl) { bind<ClientsRepository>() }
    viewModelOf(::ClientsViewModel)
}
