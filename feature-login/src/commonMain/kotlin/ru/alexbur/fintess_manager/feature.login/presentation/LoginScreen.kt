package ru.alexbur.fintess_manager.feature.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import ru.alexbur.fintess_manager.feature.login.presentation.phone.PhoneNumber
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.navigation.Route

@Composable
internal fun LoginScreen(
    navigator: Navigator,
    mainScreenRoute: Route,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()
    val onClick = remember {
        /* {
            viewModel.obtainAction(LoginAction.LoginClicked())
        } */
        { navigator.navigateTo(mainScreenRoute) }
    }
    val numberEntered = remember {
        { data: String -> viewModel.obtainAction(LoginAction.NumberEntered(data)) }
    }
    when (val state = viewState.value) {
        is LoginViewState.EnterNumber -> LoginScreen(state, onClick, numberEntered)
    }
}

@Composable
private fun LoginScreen(
    viewState: LoginViewState.EnterNumber,
    onClick: () -> Unit,
    numberEntered: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 16.dp
        )
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
}