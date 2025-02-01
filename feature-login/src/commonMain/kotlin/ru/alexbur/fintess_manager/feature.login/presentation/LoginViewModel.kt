package ru.alexbur.fintess_manager.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alexbur.fintess_manager.common_presentation.EventFlow
import ru.alexbur.fintess_manager.common_presentation.MutableEventFlow
import ru.alexbur.fintess_manager.common_presentation.mvi.Navigation
import ru.alexbur.fintess_manager.core.AppLogger
import ru.alexbur.fintess_manager.feature.login.domain.interactor.LoginInteractor
import ru.alexbur.fintess_manager.navigation.Route

internal class LoginViewModel(
    private val mainRoute: Route,
    private val interactor: LoginInteractor
) : ViewModel() {

    private var phone = ""
    private val isEnableButton: Boolean get() = phone.length == 11
    private val _viewState = MutableStateFlow<LoginViewState>(
        LoginViewState.EnterNumber(isEnableButton = isEnableButton)
    )
    val viewState: StateFlow<LoginViewState> = _viewState.asStateFlow()

    private val _viewEvent = MutableEventFlow()
    val viewEvent: EventFlow = _viewEvent

    private var otp = ""
    private var userId = 0L

    fun obtainAction(action: LoginAction) {
        when (action) {
            is LoginAction.GetCodeClicked -> getOtpCode()
            is LoginAction.NumberEntered -> {
                phone = action.data
                setupState()
            }

            is LoginAction.AuthorizationClicked -> login()
        }
    }

    private fun getOtpCode() {
        if (!isEnableButton) return
        viewModelScope.launch {
            interactor.getOtp(phone).onSuccess {
                userId = it.userId
                setupLoginState()
            }.onFailure {
                AppLogger.e("tut_otp", "Error: $it")
            }
        }
    }

    private fun login() {
        if (otp.length != 6) return
        viewModelScope.launch {
            interactor.login(userId, otp).onSuccess {
                _viewEvent.send(Navigation(mainRoute))
            }.onFailure {
                AppLogger.e("tut_login", "Error: $it")
            }
        }
    }

    private fun setupState() {
        _viewState.value = LoginViewState.EnterNumber(isEnableButton = isEnableButton)
    }

    private fun setupLoginState() {
        _viewState.value = LoginViewState.EnterOtp(isEnableButton = true)
    }
}