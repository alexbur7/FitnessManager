package ru.alexbur.fintess_manager.domain.interactor

import ru.alexbur.fintess_manager.domain.repository.RocketsRepository

class RocketsInteractor(
    private val repository: RocketsRepository,
) {

    suspend fun getLeagues() = runCatching { repository.getLeagues() }
}