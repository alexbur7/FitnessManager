package ru.alexbur.fintess_manager.feature.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel
import ru.alexbur.fintess_manager.feature.login.presentation.phone.PhoneNumber

class LoginScreenFactory {

    @Composable
    fun create(navController: NavController) {
        LoginScreen(navController)
    }
}

@Composable
private fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()
    val onClick = remember { { viewModel.obtainAction(LoginAction.LoginClicked()) } }
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
    Column {
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