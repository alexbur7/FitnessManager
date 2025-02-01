package ru.alexbur.fintess_manager.navigation.impl

import androidx.navigation.NavController
import ru.alexbur.fintess_manager.navigation.Navigator
import ru.alexbur.fintess_manager.navigation.Route

internal class NavigatorImpl(
    private val navController: NavController,
) : Navigator {

    override fun navigateTo(route: Route) {
        navController.navigate(route)
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}