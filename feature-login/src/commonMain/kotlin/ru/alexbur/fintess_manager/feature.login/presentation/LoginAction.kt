package ru.alexbur.fintess_manager.feature.login.presentation

internal sealed class LoginAction {
    class NumberEntered(val phone: String) : LoginAction()
    class OtpEntered(val otp: String) : LoginAction()
    class GetCodeClicked : LoginAction()
    class LoginClicked : LoginAction()
}