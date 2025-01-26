package ru.alexbur.fintess_manager.feature.login.presentation

internal sealed class LoginAction {
    class NumberEntered(val data: String) : LoginAction()
    class LoginClicked : LoginAction()
}