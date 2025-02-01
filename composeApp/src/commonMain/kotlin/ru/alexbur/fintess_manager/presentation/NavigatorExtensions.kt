package ru.alexbur.fintess_manager.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.presentation.impl.NavigatorImpl

@Composable
fun rememberNavigator(navController: NavController): Navigator {
    return NavigatorImpl(navController)
}