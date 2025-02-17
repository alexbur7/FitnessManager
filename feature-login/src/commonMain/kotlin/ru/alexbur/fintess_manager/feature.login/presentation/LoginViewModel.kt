package ru.alexbur.fintess_manager.feature.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alexbur.fintess_manager.common_presentation.EventFlow
import ru.alexbur.fintess_manager.common_presentation.MutableEventFlow
import ru.alexbur.fintess_manager.common_presentation.error_handler.FitnessManagerErrorHandler
import ru.alexbur.fintess_manager.common_presentation.mvi.Navigation
import ru.alexbur.fintess_manager.feature.login.domain.interactor.LoginInteractor
import ru.alexbur.fintess_manager.feature.login.presentation.navigation.LoginRoute
import ru.alexbur.fintess_manager.navigation.Route

internal class LoginViewModel(
    private val mainRoute: Route,
    private val interactor: LoginInteractor,
    private val errorHandler: FitnessManagerErrorHandler
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
                phone = action.phone
                setupEnterPhoneState()
            }

            is LoginAction.LoginClicked -> login()
            is LoginAction.OtpEntered -> {
                otp = action.otp
                setupEnterOtpState()
            }
        }
    }

    private fun getOtpCode() {
        if (!isEnableButton) return
        viewModelScope.launch {
            interactor.getOtp(phone).onSuccess {
                userId = it.userId
                otp = ""
                setupEnterOtpState()
            }.onFailure {
                _viewEvent.send(errorHandler.handleError(it))
            }
        }
    }

    private fun login() {
        if (otp.length != 6) return
        viewModelScope.launch {
            interactor.login(userId, otp).onSuccess {
                _viewEvent.send(Navigation(mainRoute, LoginRoute))
            }.onFailure {
                _viewEvent.send(errorHandler.handleError(it))
            }
        }
    }

    private fun setupEnterPhoneState() {
        _viewState.value = LoginViewState.EnterNumber(isEnableButton = isEnableButton)
    }

    private fun setupEnterOtpState() {
        _viewState.value = LoginViewState.EnterOtp(isEnableButton = otp.length == 6)
    }
}