package ru.alexbur.fintess_manager.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.alexbur.fintess_manager.feature.login.presentation.navigation.LoginRoute

@Composable
fun App() {
    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxWidth()) {
            val navController = rememberNavController()
            val navigator = rememberNavigator(navController)
            NavHost(
                navController = navController,
                startDestination = LoginRoute
            ) {
                AppComposeScreenFactory(this).create(navigator)
            }
        }
    }
}