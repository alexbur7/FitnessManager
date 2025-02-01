package ru.alexbur.fintess_manager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import ru.alexbur.fintess_manager.navigation.impl.NavigatorImpl

@Composable
fun rememberNavigator(): Navigator {
    return NavigatorImpl(rememberNavController())
}