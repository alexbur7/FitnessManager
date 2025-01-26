package ru.alexbur.fintess_manager.presentation

import ru.alexbur.fintess_manager.domain.models.Rocket

sealed class AppViewState {
    data object Initial : AppViewState()
    data class ShowRockets(val data: List<Rocket>) : AppViewState()
}