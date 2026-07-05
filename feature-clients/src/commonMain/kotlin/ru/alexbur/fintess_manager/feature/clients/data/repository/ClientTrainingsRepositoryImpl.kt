package ru.alexbur.fintess_manager.feature.clients.data.repository

import kotlinx.coroutines.withContext
import ru.alexbur.fintess_manager.core.DispatcherProvider
import ru.alexbur.fintess_manager.feature.clients.data.api.ClientTrainingsApi
import ru.alexbur.fintess_manager.feature.clients.data.mapper.ClientDetailMapper
import ru.alexbur.fintess_manager.feature.clients.data.mapper.ClientTrainingMapper
import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientDetail
import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientTraining
import ru.alexbur.fintess_manager.feature.clients.domain.repository.ClientTrainingsRepository

internal class ClientTrainingsRepositoryImpl(
    private val api: ClientTrainingsApi,
    private val detailMapper: ClientDetailMapper,
    private val trainingMapper: ClientTrainingMapper,
    private val dispatcherProvider: DispatcherProvider,
) : ClientTrainingsRepository {

    override suspend fun getClientDetail(clientId: String): ClientDetail =
        withContext(dispatcherProvider.io()) {
            detailMapper.map(api.getClientDetail(clientId))
        }

    override suspend fun getClientTrainings(clientId: String, date: String): List<ClientTraining> =
        withContext(dispatcherProvider.io()) {
            api.getClientTrainings(clientId, date).trainings.map { trainingMapper.map(it) }
        }
}
