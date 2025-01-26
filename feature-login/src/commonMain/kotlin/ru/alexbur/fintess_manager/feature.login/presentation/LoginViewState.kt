package ru.alexbur.fintess_manager.feature.login.presentation

import ru.alexbur.fintess_manager.core.mvi.ViewState

internal sealed class LoginViewState : ViewState {
    data class EnterNumber(
        val phone: String,
        val isEnableButton: Boolean,
    ) : LoginViewState()
}