package ru.alexbur.fintess_manager.feature.clients.data.mapper

import kotlinx.datetime.LocalDate
import ru.alexbur.fintess_manager.feature.clients.data.models.response.ClientDetailResponse
import ru.alexbur.fintess_manager.feature.clients.domain.models.ClientDetail

internal class ClientDetailMapper {
    fun map(dto: ClientDetailResponse) = ClientDetail(
        relationshipsId = dto.relationshipsId,
        name = "${dto.firstName} ${dto.lastName}",
        remainingWorkouts = dto.remainingWorkouts,
        workoutsStatus = dto.workoutsStatus,
        weightKg = dto.weightKg,
        heightCm = dto.heightCm,
        age = dto.age,
        clientSince = LocalDate.parse(dto.clientSince),
    )
}
