package ru.alexbur.fintess_manager.feature.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

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
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewState.phone,
            onValueChange = { newValue: String ->
                numberEntered(newValue)
            },
        )
        Button(
            onClick = onClick,
            enabled = viewState.isEnableButton,
        ) {
            Text(text = "Получить код")
        }
    }
}