package ru.alexbur.fintess_manager.feature.clients.data.mapper

import ru.alexbur.fintess_manager.feature.clients.data.models.response.ClientResponse
import ru.alexbur.fintess_manager.feature.clients.domain.models.Client

internal class ClientsMapper {
    fun map(dto: ClientResponse) = Client(
        relationshipsId = dto.relationshipsId,
        name = "${dto.firstName} ${dto.lastName}",
        remainingWorkouts = dto.remainingWorkouts,
        workoutsStatus = dto.workoutsStatus,
    )
}
