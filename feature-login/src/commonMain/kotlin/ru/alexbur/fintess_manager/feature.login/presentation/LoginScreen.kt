package ru.alexbur.fintess_manager.feature.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
                is Navigation -> navigator.navigateTo(event.route)
            }
        }
    }
    val onClick = remember { { viewModel.obtainAction(LoginAction.GetCodeClicked()) } }
    val numberEntered = remember {
        { data: String -> viewModel.obtainAction(LoginAction.NumberEntered(data)) }
    }
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 16.dp
        )
    ) {
        when (val state = viewState.value) {
            is LoginViewState.EnterNumber -> EnterPhoneScreen(state, onClick, numberEntered)
            is LoginViewState.EnterOtp -> LoginScreen(state, onClick, numberEntered)
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
    numberEntered: (String) -> Unit
) {
    Text(
        text = "Введи одноразовый пароль",
    )
    PhoneNumber(numberEntered)
    Button(
        onClick = onClick,
        enabled = viewState.isEnableButton,
    ) {
        Text(text = "Войти в профиль")
    }
}