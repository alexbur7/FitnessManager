package ru.alexbur.fintess_manager.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.alexbur.fintess_manager.feature.login.presentation.LoginScreenFactory

class AppComposeScreenFactory(
    private val builder: NavGraphBuilder,
) {

    fun create(navController: NavController) {
        builder.composable("login") { stackEntry ->
            LoginScreenFactory().create(navController)
        }
    }
}