package ru.alexbur.fintess_manager.feature.clients.data.mapper

import ru.alexbur.fintess_manager.feature.clients.data.models.response.ClientTrainingResponse
import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientTraining
import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientTrainingStatus

internal class ClientTrainingMapper {
    fun map(dto: ClientTrainingResponse) = ClientTraining(
        id = dto.id,
        startTime = dto.startTime,
        endTime = dto.endTime,
        durationMinutes = dto.durationMinutes,
        title = dto.title,
        status = ClientTrainingStatus.create(dto.status),
    )
}
