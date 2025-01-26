package ru.alexbur.fintess_manager.domain.repository

import ru.alexbur.fintess_manager.domain.models.Rocket

interface RocketsRepository {

    suspend fun getLeagues(): List<Rocket>
}