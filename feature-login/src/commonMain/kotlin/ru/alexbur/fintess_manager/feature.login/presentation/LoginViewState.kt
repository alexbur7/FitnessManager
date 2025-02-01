package ru.alexbur.fintess_manager.feature.login.presentation

import ru.alexbur.fintess_manager.common_presentation.mvi.ViewState

internal sealed class LoginViewState : ViewState {
    data class EnterNumber(
        val isEnableButton: Boolean,
    ) : LoginViewState()

    data class EnterOtp(
        val isEnableButton: Boolean,
    ) : LoginViewState()
}