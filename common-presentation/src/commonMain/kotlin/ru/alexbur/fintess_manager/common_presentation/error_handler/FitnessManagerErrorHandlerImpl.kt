package ru.alexbur.fintess_manager.common_presentation.error_handler

import ru.alexbur.fintess_manager.common_presentation.mvi.ShowSnackBar
import ru.alexbur.fintess_manager.common_presentation.mvi.ViewEvent
import ru.alexbur.fintess_manager.network.domain.ErrorModel

internal class FitnessManagerErrorHandlerImpl : FitnessManagerErrorHandler {

    override fun handleError(error: Throwable): ViewEvent {
        return when (error) {
            is ErrorModel -> ShowSnackBar(ShowSnackBar.Settings(error.message))
            else -> ShowSnackBar(ShowSnackBar.Settings(error.message ?: "Unknown Error"))
        }
    }
}