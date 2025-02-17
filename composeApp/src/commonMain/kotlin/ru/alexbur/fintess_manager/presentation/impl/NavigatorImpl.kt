package ru.alexbur.fintess_manager.presentation.impl

import androidx.navigation.NavController
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.navigation.Route

internal class NavigatorImpl(
    private val navController: NavController,
) : Navigator {

    override fun navigateTo(route: Route, clearRoute: Route?) {
        navController.navigate(route) {
            if (clearRoute != null) {
                popUpTo(clearRoute) {
                    inclusive = true
                }
            }
        }
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}