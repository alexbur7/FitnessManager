package ru.alexbur.fintess_manager.data.mappers

import ru.alexbur.fintess_manager.data.models.RocketsResponse
import ru.alexbur.fintess_manager.domain.models.Rocket

internal class RocketMappers {

    fun map(response: RocketsResponse) = Rocket(
        id = response.id,
        active = response.active,
        stages = response.stages,
        country = response.country,
    )
}