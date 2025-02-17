package ru.alexbur.fintess_manager.network.data.mappers

import ru.alexbur.fintess_manager.network.data.models.ErrorModelResponse
import ru.alexbur.fintess_manager.network.domain.ErrorModel

internal class ErrorMapper {

    fun map(response: ErrorModelResponse) = ErrorModel(
        message = response.message,
        code = response.code,
    )
}