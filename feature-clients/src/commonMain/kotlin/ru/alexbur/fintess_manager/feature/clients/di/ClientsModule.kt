package ru.alexbur.fintess_manager.feature.clients.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.alexbur.fintess_manager.feature.clients.data.api.ClientTrainingsApi
import ru.alexbur.fintess_manager.feature.clients.data.api.ClientsApi
import ru.alexbur.fintess_manager.feature.clients.data.mapper.ClientDetailMapper
import ru.alexbur.fintess_manager.feature.clients.data.mapper.ClientTrainingMapper
import ru.alexbur.fintess_manager.feature.clients.data.mapper.ClientsMapper
import ru.alexbur.fintess_manager.feature.clients.data.repository.ClientTrainingsRepositoryImpl
import ru.alexbur.fintess_manager.feature.clients.data.repository.ClientsRepositoryImpl
import ru.alexbur.fintess_manager.feature.clients.domain.interactor.ClientTrainingsInteractor
import ru.alexbur.fintess_manager.feature.clients.domain.interactor.ClientsInteractor
import ru.alexbur.fintess_manager.feature.clients.domain.repository.ClientTrainingsRepository
import ru.alexbur.fintess_manager.feature.clients.domain.repository.ClientsRepository
import ru.alexbur.fintess_manager.feature.clients.presentation.detail.ClientTrainingsViewModel
import ru.alexbur.fintess_manager.feature.clients.presentation.list.ClientsViewModel

val clientsModule = module {
    singleOf(::ClientsApi)
    factoryOf(::ClientsMapper)
    factoryOf(::ClientsInteractor)
    factoryOf(::ClientsRepositoryImpl) { bind<ClientsRepository>() }
    viewModelOf(::ClientsViewModel)

    singleOf(::ClientTrainingsApi)
    factoryOf(::ClientDetailMapper)
    factoryOf(::ClientTrainingMapper)
    factoryOf(::ClientTrainingsInteractor)
    factoryOf(::ClientTrainingsRepositoryImpl) { bind<ClientTrainingsRepository>() }
    // Явная форма — НЕ viewModelOf(::ClientTrainingsViewModel): viewModelOf резолвит параметры
    // конструктора обычным Scope.get<T>(), игнорируя ParametersHolder из koinViewModel { ... } на
    // месте вызова, что привело бы к NoDefinitionFoundException в рантайме при первом клике на
    // клиента (см. plan.md Decisions Made — прецедент LoginModule.kt:16-18).
    viewModel { parameters ->
        ClientTrainingsViewModel(parameters.get(), parameters.get(), parameters.get(), parameters.get(), get(), get())
    }
}
