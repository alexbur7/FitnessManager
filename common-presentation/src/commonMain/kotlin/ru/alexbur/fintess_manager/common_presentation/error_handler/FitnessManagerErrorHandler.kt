package ru.alexbur.fintess_manager.common_presentation.error_handler

import ru.alexbur.fintess_manager.common_presentation.mvi.ViewEvent

interface FitnessManagerErrorHandler {
    fun handleError(error: Throwable): ViewEvent
}