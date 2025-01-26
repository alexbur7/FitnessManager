package ru.alexbur.fintess_manager.data.repository

import kotlinx.coroutines.withContext
import ru.alexbur.fintess_manager.core.DispatcherProvider
import ru.alexbur.fintess_manager.data.api.FitnessManagerApi
import ru.alexbur.fintess_manager.data.mappers.RocketMappers
import ru.alexbur.fintess_manager.domain.models.Rocket
import ru.alexbur.fintess_manager.domain.repository.RocketsRepository

internal class RocketsRepositoryImpl(
    private val api: FitnessManagerApi,
    private val dispatcherProvider: DispatcherProvider,
    private val mapper: RocketMappers,
) : RocketsRepository {

    override suspend fun getLeagues(): List<Rocket> = withContext(dispatcherProvider.io()) {
        api.getRockets().map { mapper.map(it) }
    }
}