package ru.alexbur.fintess_manager.feature.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.ParametersHolder
import ru.alexbur.fintess_manager.common_presentation.mvi.Navigation
import ru.alexbur.fintess_manager.feature.login.presentation.phone.PhoneNumber
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.navigation.Route

@Composable
internal fun LoginScreen(
    navigator: Navigator,
    mainScreenRoute: Route,
    viewModel: LoginViewModel = koinViewModel { ParametersHolder(mutableListOf(mainScreenRoute)) },
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect { event ->
            when (event) {
                is Navigation -> {
                    navigator.navigateTo(event.route)
                }
            }
        }
    }
    val onClick = remember { { viewModel.obtainAction(LoginAction.GetCodeClicked()) } }
    val loginClick = remember { { viewModel.obtainAction(LoginAction.LoginClicked()) } }
    val numberEntered = remember {
        { data: String -> viewModel.obtainAction(LoginAction.NumberEntered(data)) }
    }
    val otpEntered = remember { { data: String -> viewModel.obtainAction(LoginAction.OtpEntered(data)) } }
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 16.dp
        )
    ) {
        when (val state = viewState.value) {
            is LoginViewState.EnterNumber -> EnterPhoneScreen(state, onClick, numberEntered)
            is LoginViewState.EnterOtp -> LoginScreen(state, loginClick, otpEntered)
        }
    }
}

@Composable
private fun ColumnScope.EnterPhoneScreen(
    viewState: LoginViewState.EnterNumber,
    onClick: () -> Unit,
    numberEntered: (String) -> Unit
) {
    Text(
        text = "Введи номер телефона",
    )
    PhoneNumber(numberEntered)
    Button(
        onClick = onClick,
        enabled = viewState.isEnableButton,
    ) {
        Text(text = "Получить код")
    }
}

@Composable
private fun ColumnScope.LoginScreen(
    viewState: LoginViewState.EnterOtp,
    onClick: () -> Unit,
    otpEntered: (String) -> Unit
) {
    var otp = rememberSaveable { mutableStateOf("") }
    Text(
        text = "Введи одноразовый пароль",
    )
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = otp.value,
        onValueChange = { data: String ->
            otp.value = data
            otpEntered(otp.value)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    Button(
        onClick = onClick,
        enabled = viewState.isEnableButton,
    ) {
        Text(text = "Войти в профиль")
    }
}