package ru.alexbur.fintess_manager.feature.login.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class LoginViewModel : ViewModel() {

    private var phone = ""
    private val _viewState = MutableStateFlow<LoginViewState>(
        LoginViewState.EnterNumber(
            phone = phone,
            isEnableButton = false
        )
    )
    val viewState: StateFlow<LoginViewState> = _viewState.asStateFlow()

    fun obtainAction(action: LoginAction) {
        when (action) {
            is LoginAction.LoginClicked -> Unit
            is LoginAction.NumberEntered -> {
                phone = action.data
                setupState()
            }
        }
    }

    private fun setupState() {
        _viewState.value = LoginViewState.EnterNumber(
            phone = phone,
            isEnableButton = phone.isNotBlank()
        )
    }
}